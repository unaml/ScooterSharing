package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentStartRideBinding
import dk.itu.moapd.scootersharing.interfaces.ItemClickListener
import dk.itu.moapd.scootersharing.models.Scooter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.R

private const val TAG = "StartRideActivity"
/**
 * A simple [Fragment] subclass.
 * Use the [StartRideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartRideFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentStartRideBinding? = null
    //Setting up authentication
    private lateinit var auth : FirebaseAuth
    //Setting up the database
    private lateinit var database : DatabaseReference


    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!


    companion object {
        private lateinit var adapter: ScooterArrayAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize FireBase Auth.
        auth = FirebaseAuth.getInstance()
        //Connect to realtime database
        database = Firebase.database(DATABASE_URL).reference
        // Enable offline capabilities.
        database.keepSynced(true)

        //Create search query
        val query = database.child("scooters")
            .child(auth.currentUser?.uid ?: "None")
            .orderByChild("createdAt")
        // Execute a query in the database to fetch appropriate data.
        val options = FirebaseRecyclerOptions.Builder<Scooter>()
            .setQuery(query, Scooter::class.java)
            .setLifecycleOwner(this)
            .build()
        //Create the custom adapter to bind a list of dummy objects
        adapter = ScooterArrayAdapter(this, options)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding between layout and fragment
        _binding = FragmentStartRideBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return (binding.root)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fm = parentFragmentManager

        with(binding) {
            startButton.setOnClickListener {
                val name = nameText.text.toString().trim()
                if (nameText.text.isNotEmpty()) {
                    val timestamp = System.currentTimeMillis()
                    val scooter = Scooter(name, timestamp, 100, true, timestamp, 0.0,0.0)

                    val uid = database.child("scooters")
                        .child(auth.currentUser?.uid!!)
                        .push()
                        .key

                    database.child("scooters")
                        .child(auth.currentUser?.uid!!)
                        .child(uid!!)
                        .setValue(scooter)
                }

                //Open map
                fm
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, MapsFragment())
                    .commit()
                Log.d(TAG, "StartRide called")

            }
            goBack.setOnClickListener{

                fm
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, ScooterSharingFragment())
                    .commit()
                Log.d(TAG, "Main Screen called")
            }

        }
    }

    private fun updateUi () {
        TODO("Not yet implemented")
    }

    override fun onItemClickListener(scooter: Scooter, position: Int) {
        TODO("Not yet implemented")
    }
}
