package dk.itu.moapd.scootersharing.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.models.RidesDB
import dk.itu.moapd.scootersharing.activities.LoginActivity
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding


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
        private lateinit var database : DatabaseReference

        /**
         * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
        */
        private val binding get() = _binding!!

        //  A set of static attributes used in this activity class.
        companion object {
            lateinit var ridesDB : RidesDB
            private lateinit var adapter : ScooterArrayAdapter
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
                        .replace(R.id.fragment_container_view_tag, StartRideFragment())
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
                    ridesRecyclerView.adapter = ScooterSharingFragment.adapter
                }
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

    private fun startLoginActivity() {
        val intent = Intent(this@ScooterSharingFragment.context, LoginActivity::class.java)
        startActivity(intent)
        //finish()
    }
}

