package dk.itu.moapd.scootersharing.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.models.Scooter

class MapsFragment : Fragment(), OnMapsSdkInitializedCallback {

    /**
     * A set of private constants used in this class.
     */
    companion object {
        private val TAG = MapsFragment::class.qualifiedName
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        // Add a marker in ITU and move the camera
        val itu = LatLng(55.6596, 12.5910)
        Firebase.database(DATABASE_URL).reference.child("scooters").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(postSnapshot in dataSnapshot.children) {
                    var tempScooter = postSnapshot.getValue<Scooter>()
                    var tempPosition = tempScooter?.let { LatLng(it.getLat(), tempScooter.getLon()) }
                    tempPosition?.let {
                        MarkerOptions()
                            .position(it)
                            .title("Hey")
                    }?.let {
                        googleMap.addMarker(
                            it

                        )
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //If post failed, log message
                Log.d(TAG, "Loadpost: onCancelled")
            }

        })
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(itu, 18f))
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        if (!checkPermission()) {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(requireContext(), Renderer.LATEST, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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
     * This method determines which version of the renderer was returned.
     *
     * @param renderer The renderer used in the Google Maps
     */
    override fun onMapsSdkInitialized(renderer: Renderer) {
        when (renderer) {
            Renderer.LATEST ->
                Log.d(TAG, "The latest version of the renderer is used.")
            Renderer.LEGACY ->
                Log.d(TAG, "The legacy version of the renderer is used.")
        }
    }
}