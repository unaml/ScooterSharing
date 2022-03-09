package dk.itu.moapd.scootersharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "ScooterSharingActivity"

class ScooterSharingActivity : AppCompatActivity() {

    //View binding for ScooterSharingActivity
    private lateinit var binding : ActivityScooterSharingBinding

    //  A set of static attributes used in this activity class.
    companion object {
        lateinit var ridesDB : RidesDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scooter_sharing)

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

        binding = ActivityScooterSharingBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

    }
}