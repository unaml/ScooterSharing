package dk.itu.moapd.scootersharing.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.models.RidesDB
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding
import dk.itu.moapd.scootersharing.fragments.LocationFragment
import dk.itu.moapd.scootersharing.fragments.MapsFragment
import dk.itu.moapd.scootersharing.fragments.ScooterSharingFragment
import dk.itu.moapd.scootersharing.models.ScooterSharingVM

//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "ScooterSharingActivity"

class ScooterSharingActivity : AppCompatActivity() {

    //View binding for ScooterSharingActivity
    private lateinit var binding : ActivityScooterSharingBinding
    //Shared preferences for saving the current state
    private lateinit var preferences : SharedPreferences


    //  A set of static attributes used in this activity class.
    companion object {
        lateinit var ridesDB : RidesDB
    }

    /**
     * Using lazy initialization to create the view model instance when the user access the object
     * for the first time.
     */
    private val viewModel: ScooterSharingVM by lazy {
        ViewModelProvider(this)
            .get(ScooterSharingVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_scooter_sharing) i think i can delete this

        //Binding for layout and activity
        binding = ActivityScooterSharingBinding.inflate(layoutInflater)
        // Get the shared preferences instance.
        //TODO: add shared preferences


        // Get the latest fragment added in the fragment manager.
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag)

        // Create the fragments instances.
        if (currentFragment == null) {
            viewModel.addFragment(LocationFragment())
            viewModel.addFragment(MapsFragment())
            viewModel.setFragment(0)
        }

        // Add the fragment into the activity.
        if (currentFragment == null) {
            val fragment = ScooterSharingFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view_tag, fragment)
                .commit()
        }

        setContentView(binding.root)

    }


}