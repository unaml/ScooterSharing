package dk.itu.moapd.scootersharing.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.activities.LoginActivity
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import dk.itu.moapd.scootersharing.interfaces.ItemClickListener
import dk.itu.moapd.scootersharing.models.Scooter


private const val TAG = "ScooterSharingFragment"
//Firebase Realtime Database URL.
const val DATABASE_URL =
    "https://scootersharing-authentication-default-rtdb.europe-west1.firebasedatabase.app/"
/**
 * A simple [Fragment] subclass.
 * Use the [ScooterSharingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScooterSharingFragment : Fragment(), ItemClickListener {

        //View binding for ScooterSharingActivity
        private var _binding : FragmentScooterSharingBinding? = null
    //Setting up authentication
    private lateinit var auth : FirebaseAuth
        //Setting up the database
        private lateinit var database : DatabaseReference


    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

        //  A set of static attributes used in this activity class.
        companion object {
            private lateinit var adapter : ScooterArrayAdapter
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

            // Create the search query.
            val query = database.child("scooters")
                .child(auth.currentUser?.uid ?: "None")
                .orderByChild("createdAt")

            // A class provide by FirebaseUI to make a query in the database to fetch appropriate data.
            val options = FirebaseRecyclerOptions.Builder<Scooter>()
                .setQuery(query, Scooter::class.java)
                .setLifecycleOwner(this)
                .build()


            val fm = parentFragmentManager


            adapter = ScooterArrayAdapter(this, options)

            // Define the recycler view layout manager.
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            binding.recyclerView.adapter = adapter


            with(binding){
                //Start button
                startButton.setOnClickListener{
                    //Start the application
                    fm
                        .beginTransaction()
                        .replace(R.id.fragment_container_view_tag, MapsFragment())
                        .commit()
                    Log.d(TAG, "StartRide called")
                }
                //Edit button
                editButton?.setOnClickListener{
                    //Edit ride
                    Log.d(TAG, "EditRide called")
                    fm
                        .beginTransaction()
                        .replace(R.id.fragment_container_view_tag, EditRideFragment())
                        .commit()
                }
                //List button
                listButton.setOnClickListener{
                    recyclerView.adapter = adapter
                }
            }

            }


    override fun onItemClickListener(scooter: Scooter, position: Int) {
        TODO("Not yet implemented")
    }

}

