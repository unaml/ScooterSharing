package dk.itu.moapd.scootersharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
//import dk.itu.moapd.scootersharing.databinding.ActivityMainBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "ScooterSharingActivity"

class ScooterSharingActivity : AppCompatActivity() {

    //GUI variables
    private lateinit var startButton : Button
    private lateinit var editButton : Button

    companion object {
        lateinit var ridesDB : RidesDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        //Singleton to share an object between activites
        ridesDB = RidesDB.get(this)

        setContentView(R.layout.activity_scooter_sharing)

        //Start button
        startButton = findViewById(R.id.start_button)
        startButton.setOnClickListener{
            //Start the application
            Log.d(TAG, "StartRide called")
            val intent = Intent(this, StartRideActivity::class.java)
            startActivity(intent)
        }
        //Edit button
        editButton = findViewById(R.id.edit_button)
        editButton.setOnClickListener{
            //Edit ride
            Log.d(TAG, "EditRide called")
            val intent = Intent(this, EditRideActivity::class.java)
            startActivity(intent)
        }
    }

}