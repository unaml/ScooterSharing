package dk.itu.moapd.scootersharing.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.fragments.LocationFragment
import dk.itu.moapd.scootersharing.fragments.MapsFragment
import dk.itu.moapd.scootersharing.models.ScooterSharingVM
import dk.itu.moapd.scootersharing.activities.LoginActivity
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding
import dk.itu.moapd.scootersharing.fragments.PaymentFragment
import dk.itu.moapd.scootersharing.fragments.ScooterSharingFragment
import dk.itu.moapd.scootersharing.models.RidesDB
import java.util.concurrent.TimeUnit
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "ScooterSharingActivity"

class ScooterSharingActivity : AppCompatActivity() {

    //View binding for ScooterSharingActivity
    private lateinit var binding : ActivityScooterSharingBinding
    //The primary instance for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    //This callback is called when `FusedLocationProviderClient` has a new `Location`.
    private lateinit var locationCallback: LocationCallback
    //Setting up authentication
    private lateinit var auth : FirebaseAuth


    //  A set of static attributes used in this activity class.
    companion object {
        private const val ALL_PERMISSIONS_RESULT = 1011
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
        //FirebaseAuth.getInstance().signOut()
        //setContentView(R.layout.activity_scooter_sharing) i think i can delete this

        //Initialize FireBase Auth.
        auth = FirebaseAuth.getInstance()

        //Binding for layout and activity
        binding = ActivityScooterSharingBinding.inflate(layoutInflater)


        // Get the latest fragment added in the fragment manager.
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_tag)

        // Create the fragments instances.
        if (currentFragment == null) {
            viewModel.addFragment(ScooterSharingFragment())
            viewModel.addFragment(LocationFragment())
            viewModel.addFragment(MapsFragment())
            //viewModel.addFragment(PaymentFragment())
            viewModel.setFragment(0)
        }

        // Add the fragment into the activity.
        for (fragment in viewModel.getFragmentList())
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view_tag, fragment)
                .hide(fragment)
                .commit()


        // The current activity.
        var activeFragment: Fragment = viewModel.fragmentState.value!!

        // Execute this when the user sets a specific fragment.
        viewModel.fragmentState.observe(this) { fragment ->
            supportFragmentManager
                .beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            activeFragment = fragment
        }
        // Start the location-aware method.
        startLocationAware()

        // Firebase Sign Out.
        binding.topAppBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.signout -> {
                    AuthUI.getInstance().signOut(applicationContext).addOnCompleteListener {
                        startLoginActivity()
                    }
                    true
                }
                else -> false
            }
        }

        // Inflate the user interface into the current activity.
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser == null)
            startLoginActivity()
        val user = auth.currentUser
       /** binding.description?.text = getString(
            R.string.firebase_user_description,
            user?.email ?: user?.phoneNumber
        )*/
    }

    override fun onResume() {
        super.onResume()
        subscribeToLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        unsubscribeToLocationUpdates()
    }

    /**
     * Start the location-aware instance and defines the callback to be called when the GPS sensor
     * provides a new user's location.
     */
    private fun startLocationAware() {

        // Show a dialog to ask the user to allow the application to access the device's location.
        requestUserPermissions()

        // Start receiving location updates.
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)

        // Initialize the `LocationCallback`.
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                viewModel.onLocationChanged(locationResult.lastLocation)
            }
        }
    }

    /**
     * Create a set of dialogs to show to the users and ask them for permissions to get the device's
     * resources.
     */
    private fun requestUserPermissions() {
        // An array with location-aware permissions.
        val permissions: ArrayList<String> = ArrayList()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

        // Check which permissions is needed to ask to the user.
        val permissionsToRequest = permissionsToRequest(permissions)

        // Show the permissions dialogs to the user.
        if (permissionsToRequest.size > 0)
            requestPermissions(
                permissionsToRequest.toTypedArray(),
                ALL_PERMISSIONS_RESULT
            )
    }

    /**
     * Create an array with the permissions to show to the user.
     *
     * @param permissions An array with the permissions needed by this applications.
     *
     * @return An array with the permissions needed to ask to the user.
     */
    private fun permissionsToRequest(permissions: ArrayList<String>): ArrayList<String> {
        val result: ArrayList<String> = ArrayList()
        for (permission in permissions)
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                result.add(permission)
        return result
    }

    /**
     * This method checks if the user allows the application uses all location-aware resources to
     * monitor the user's location.
     *
     * @return A boolean value with the user permission agreement.
     */
    private fun checkPermission() =
        ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED

    /**
     * Subscribes this application to get the location changes via the `locationCallback()`.
     */
    @SuppressLint("MissingPermission")
    private fun subscribeToLocationUpdates() {
        // Check if the user allows the application to access the location-aware resources.
        if (checkPermission())
            return

        // Sets the accuracy and desired interval for active location updates.
        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(5)
            fastestInterval = TimeUnit.SECONDS.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // Subscribe to location changes.
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    /**
     * Unsubscribes this application of getting the location changes from  the `locationCallback()`.
     */
    private fun unsubscribeToLocationUpdates() {
        // Unsubscribe to location changes.
        fusedLocationProviderClient
            .removeLocationUpdates(locationCallback)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        //finish()
    }
}