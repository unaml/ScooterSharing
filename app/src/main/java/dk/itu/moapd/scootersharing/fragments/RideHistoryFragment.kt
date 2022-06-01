package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.DATABASE_URL
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentRideHistoryBinding
import dk.itu.moapd.scootersharing.interfaces.ItemClickListener
import dk.itu.moapd.scootersharing.models.Rides

/**
 * A simple [Fragment] subclass.
 * Use the [RideHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "RideHistoryFragment"
class RideHistoryFragment : Fragment(), ItemClickListener {

    private var _binding : FragmentRideHistoryBinding? = null
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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ride_history_element, container, false)

        //Binding between layout and fragment
        _binding = FragmentRideHistoryBinding.inflate(layoutInflater)

        //Initialize FireBase Auth.
        auth = FirebaseAuth.getInstance()
        //Connect to realtime database
        database = Firebase.database(DATABASE_URL).reference

        // Enable offline capabilities.
        database.keepSynced(true)

        // Create the search query.
        val query = database.child("rides")
            .child(auth.currentUser?.uid ?: "None")
            //.orderByChild("endTime")

        // A class provide by FirebaseUI to make a query in the database to fetch appropriate data.
        val options = FirebaseRecyclerOptions.Builder<Rides>()
            .setQuery(query, Rides::class.java)
            .setLifecycleOwner(this)
            .build()


        RideHistoryFragment.adapter = ScooterArrayAdapter(this, options)

        // Define the recycler view layout manager.
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        /*binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )*/
        binding.recyclerView.adapter = adapter


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onItemClickListener(rides: Rides, position: Int) {
        TODO("Not yet implemented")
    }

}