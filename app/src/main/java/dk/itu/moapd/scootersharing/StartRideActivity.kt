package dk.itu.moapd.scootersharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//import dk.itu.moapd.scootersharing.databinding.ActivityMainBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "StartRideActivity"
class StartRideActivity : AppCompatActivity() {

    //GUI variables
    private lateinit var infoText : EditText
    private lateinit var startButton : Button
    private lateinit var nameText : TextView
    private lateinit var whereText : TextView

    private val scooter : Scooter = Scooter ("", "", 0)
    companion object {
        lateinit var ridesDB : RidesDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate called")
        //Singleton to share an object between activites
        ScooterSharingActivity.ridesDB = RidesDB.get(this)
        setContentView(R.layout.activity_start_ride)

        //Edit texts
        infoText = findViewById(R.id.info_text) //TODO : change r.id
        whereText = findViewById(R.id.where_text)
        nameText = findViewById(R.id.name_text)
        //Buttons
        startButton = findViewById(R.id.start_button) //TODO: fix r.id
        startButton.setOnClickListener{
            if(nameText.text.isNotEmpty() && whereText.text.isNotEmpty()){
                //Update the object attributes
                val name = nameText.text.toString().trim()
                val where = whereText.text.toString().trim()
                scooter.name = name
                scooter.where = where
                scooter.timestamp = System.currentTimeMillis()
                //Reset
                nameText.setText("")
                whereText.setText("")
                updateUi()
            }
        }
    }
    private fun updateUi () {
        infoText.setText(scooter.toString () )
    }
}