package dk.itu.moapd.scootersharing.fragments

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.google.firebase.database.DatabaseReference
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentMarkerBinding
import dk.itu.moapd.scootersharing.databinding.FragmentPaymentBinding
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding

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

    companion object {
        private val TAG = MarkerFragment::class.qualifiedName
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        // Add a marker in ITU and move the camera
        val itu = LatLng(55.6596, 12.5910)
        googleMap.addMarker(
            MarkerOptions()
            .position(itu)
            .title("Marker in IT University of Copenhagen")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(itu, 18f))
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

       /** if (!checkPermission()) {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }*/
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