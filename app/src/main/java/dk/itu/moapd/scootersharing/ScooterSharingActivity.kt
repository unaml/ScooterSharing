package dk.itu.moapd.scootersharing

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "ScooterSharingActivity"

class ScooterSharingActivity : AppCompatActivity() {

    //View binding for ScooterSharingActivity
    private lateinit var binding : ActivityScooterSharingBinding
    //Shared preferences for saving the current state
    private lateinit var preferences : SharedPreferences
    //Setting up authentication
    private lateinit var auth : FirebaseAuth

    //  A set of static attributes used in this activity class.
    companion object {
        lateinit var ridesDB : RidesDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scooter_sharing)
        //Binding for layout and activity
        binding = ActivityScooterSharingBinding.inflate(layoutInflater)
        // Get the shared preferences instance.

        //Initialize FireBase Auth.
        auth = FirebaseAuth.getInstance()

        // Get the latest fragment added in the fragment manager.
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag)

        // Add the fragment into the activity.
        if (currentFragment == null) {
            val fragment = ScooterSharingFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view_tag, fragment)
                .commit()
        }

        // Firebase Sign Out.
        binding.topAppBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.more -> {
                    auth.signOut()
                    startLoginActivity()
                    true
                }
                else -> false
            }
        }

        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser == null)
            startLoginActivity()
        val user = auth.currentUser
        //BINDING DESCRIPTION?
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}