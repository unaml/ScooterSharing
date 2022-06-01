package dk.itu.moapd.scootersharing

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

const val DATABASE_URL =
    "https://scootersharing-authentication-default-rtdb.europe-west1.firebasedatabase.app/"

class ScooterSharingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.database(DATABASE_URL).setPersistenceEnabled(true)
    }
}