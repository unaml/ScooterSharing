package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.DATABASE_URL
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding
import dk.itu.moapd.scootersharing.interfaces.ItemClickListener
import dk.itu.moapd.scootersharing.models.Rides


private const val TAG = "ScooterSharingFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [ScooterSharingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScooterSharingFragment : Fragment(), ItemClickListener {

    //View binding for ScooterSharingActivity
    private var _binding: FragmentScooterSharingBinding? = null

    //Setting up authentication
    private lateinit var auth: FirebaseAuth

    //Setting up the database
    private lateinit var database: DatabaseReference


    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

    //  A set of static attributes used in this activity class.
    companion object {
        private lateinit var adapter: ScooterArrayAdapter
        private const val ALL_PERMISSIONS_RESULT = 1011
    }

    /**
     * Using lazy initialization to create the view model instance when the user access the object
     * for the first time.
     */

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

        // Enable offline capabilities.
        database.keepSynced(true)


        val fm = parentFragmentManager

        with(binding) {
            //Start button
            startButton.setOnClickListener {
                //Start the application
                fm
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, MapsFragment())
                    .commit()
                Log.d(TAG, "StartRide called")
            }
            //RentalHistory button
            rental.setOnClickListener {
                //Edit ride
                Log.d(TAG, "EditRide called")
                fm
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, RideHistoryFragment())
                    .commit()
            }
        }
    }


    override fun onItemClickListener(rides: Rides, position: Int) {
        TODO("Not yet implemented")
    }

}

