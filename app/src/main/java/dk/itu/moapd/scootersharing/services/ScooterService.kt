package dk.itu.moapd.scootersharing.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.Service
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.IBinder
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.DATABASE_URL
import dk.itu.moapd.scootersharing.R.drawable.ic_baseline_electric_scooter_24
import dk.itu.moapd.scootersharing.models.Rides
import java.lang.Double.max
import java.util.*

class ScooterService : Service() {
    private lateinit var scooter: String
    private lateinit var start: Date
    private var started = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.extras?.run {
            if (getString("command") == "start" && !started) {
                scooter = this.getString("scooter")!!
                start = Date()
                started = true

                val notificationManager =
                    baseContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                if (SDK_INT >= O) NotificationChannel("id", "name", IMPORTANCE_DEFAULT).run {
                    notificationManager.createNotificationChannel(this.apply {
                        description = "description"
                    })
                }

                val notificationBuilder = Builder(applicationContext, "current_ride").apply {
                    setContentTitle("Scooter Sharing")
                    setContentText("Scooter #73482734")
                    setSmallIcon(ic_baseline_electric_scooter_24)
                    setVisibility(VISIBILITY_PUBLIC)
                    setChannelId("id")
                    setAutoCancel(true)
                }

                notificationBuilder.build().run {
                    notificationManager.notify(99, this)
                    startForeground(99, this)
                }
            } else if (getString("command") == "end" && started) {
                started = false
                val now = Date()

                sendBroadcast(Intent("finished").apply {
                    putExtra("elapsed", now.time - start.time)
                })

                val db = Firebase.database(DATABASE_URL).reference
                val id = db.ref.push().key!!
                db.child("rides")
                    .child(getInstance().currentUser?.uid ?: "None")
                    .child(id)
                    .setValue(
                        Rides(
                            scooter = scooter,
                            startTime = start.time,
                            endTime = now.time,
                            price = max(Date(now.time - start.time).minutes * 0.4, 10.0)
                        )
                    )

                if (SDK_INT >= O) {
                    stopForeground(true)
                } else {
                    stopSelf()
                }
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null
}