package dk.itu.moapd.scootersharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dk.itu.moapd.scootersharing.databinding.ActivityEditRideBinding
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding

//import dk.itu.moapd.scootersharing.databinding.ActivityMainBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
    private const val TAG = "EditRideActivity"
class EditRideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditRideBinding
    //GUI variables
    private lateinit var infoText : EditText
    private lateinit var updateButton : Button
    private lateinit var nameText : TextView
    private lateinit var whereText : TextView

    private val scooter : Scooter = Scooter ("", "", 0)
    companion object {
        lateinit var ridesDB : RidesDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ride)

        // Get the latest fragment added in the fragment manager.
        val currentFragment =
            supportFragmentManager.findFragmentById(androidx.fragment.R.id.fragment_container_view_tag)

        // Add the fragment into the activity.
        if (currentFragment == null) {
            val fragment = ScooterSharingFragment()
            supportFragmentManager
                .beginTransaction()
                .add(androidx.fragment.R.id.fragment_container_view_tag, fragment)
                .commit()
        }

        binding = ActivityEditRideBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

}