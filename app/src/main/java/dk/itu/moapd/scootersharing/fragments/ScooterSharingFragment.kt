package dk.itu.moapd.scootersharing.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.models.RidesDB
import dk.itu.moapd.scootersharing.activities.LoginActivity
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding
import dk.itu.moapd.scootersharing.models.ScooterSharingVM
import java.util.concurrent.TimeUnit


private const val TAG = "ScooterSharingFragment"
//Firebase Realtime Database URL.
const val DATABASE_URL =
    "https://scootersharing-authentication-default-rtdb.europe-west1.firebasedatabase.app/"
/**
 * A simple [Fragment] subclass.
 * Use the [ScooterSharingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScooterSharingFragment : Fragment(){

        //View binding for ScooterSharingActivity
        private var _binding : FragmentScooterSharingBinding? = null
        private lateinit var ridesRecyclerView: RecyclerView
        //Setting up authentication
        private lateinit var auth : FirebaseAuth
        //Setting up the database
        private lateinit var database : DatabaseReference
        //The primary instance for receiving location updates.
        private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
        //This callback is called when `FusedLocationProviderClient` has a new `Location`.
        private lateinit var locationCallback: LocationCallback

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

        //  A set of static attributes used in this activity class.
        companion object {
            lateinit var ridesDB : RidesDB
            private lateinit var adapter : ScooterArrayAdapter
            private const val ALL_PERMISSIONS_RESULT = 1011
        }

        /**
     * Using lazy initialization to create the view model instance when the user access the object
     * for the first time.
     */
        private val viewModel: ScooterSharingVM by lazy {
            ViewModelProvider(this)
             .get(ScooterSharingVM::class.java)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentScooterSharingBinding.inflate(layoutInflater)
            return (binding.root)
            }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
         }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            //Initialize FireBase Auth.
            auth = FirebaseAuth.getInstance()
            //Connect to realtime database
            database = Firebase.database(DATABASE_URL).reference


            //Singleton to share an object between activites
            ridesDB = RidesDB.get(requireContext())
            val rides = ridesDB.getScooters()
            val fm = parentFragmentManager


            //ScooterSharingFragment.adapter = ScooterArrayAdapter(ridesDB.getScooters()) TODO: uncomment
            //(requireContext(), R.layout.list_rides, rides)

            with(binding){
                //Start button
                startButton.setOnClickListener{
                    //Start the application
                    fm
                        .beginTransaction()
                            //change this with StartRidefragment
                        .replace(R.id.fragment_container_view_tag, MapsFragment())
                        .commit()
                    Log.d(TAG, "StartRide called")
                }
                //Edit button
                editButton.setOnClickListener{
                    //Edit ride
                    Log.d(TAG, "EditRide called")
                    fm
                        .beginTransaction()
                        .replace(R.id.fragment_container_view_tag, EditRideFragment())
                        .commit()
                }
                //List button
                listButton.setOnClickListener{
                    ridesRecyclerView.adapter = adapter
                }
            }

            // Start the location-aware method.
            startLocationAware()

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

            }

        override fun onStart() {
            super.onStart()
            if(auth.currentUser == null)
                startLoginActivity()
                val user = auth.currentUser
                binding.description?.text = getString(
                R.string.firebase_user_description,
            user?.email ?: user?.phoneNumber
        )
    }

    override fun onResume() {
        super.onResume()
        subscribeToLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        unsubscribeToLocationUpdates()
    }

    private fun startLoginActivity() {
        val intent = Intent(this@ScooterSharingFragment.context, LoginActivity::class.java)
        startActivity(intent)
        //finish()
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
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
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

}
}

