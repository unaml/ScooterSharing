package dk.itu.moapd.scootersharing.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentMarkerBinding
import dk.itu.moapd.scootersharing.databinding.FragmentPaymentBinding
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding
import dk.itu.moapd.scootersharing.models.Scooter

private const val TAG = "MarkerFragment"

class MarkerFragment : Fragment() {

    //View binding for ScooterSharingActivity
    private var _binding : FragmentMarkerBinding?= null
    private lateinit var ridesRecyclerView: RecyclerView
    //Setting up authentication
    private lateinit var auth : FirebaseAuth
    //Setting up the database
    private lateinit var database : DatabaseReference

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!
    val postSnapshot = null

    companion object {
        private val TAG = MarkerFragment::class.qualifiedName
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        Firebase.database(DATABASE_URL).reference.child("scooters").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(postSnapshot in dataSnapshot.children) {
                    var tempScooter = postSnapshot.getValue<Scooter>()
                    var tempPosition = tempScooter?.let { LatLng(it.getLat(), tempScooter.getLon()) }
                    if (tempScooter != null) {
                        tempPosition?.let {
                            MarkerOptions()
                                .position(it)
                                .title(tempScooter.getScooterName())
                        }?.let {
                            googleMap.addMarker(
                                it
                            )
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //If post failed, log message
                Log.d(TAG, "Loadpost: onCancelled")
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarkerBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}