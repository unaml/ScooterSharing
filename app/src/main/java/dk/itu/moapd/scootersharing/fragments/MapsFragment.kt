package dk.itu.moapd.scootersharing.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.maps.*
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.BarcodeScannerOptions.Builder
import com.google.mlkit.vision.barcode.BarcodeScanning.getClient
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.R.id.fragment_container_view_tag
import dk.itu.moapd.scootersharing.databinding.FragmentMapsBinding
import dk.itu.moapd.scootersharing.models.Rides
import dk.itu.moapd.scootersharing.models.Scooter
import dk.itu.moapd.scootersharing.services.ScooterService

class MapsFragment : Fragment(), OnMapsSdkInitializedCallback, View.OnClickListener {

    private lateinit var mapsBinding: FragmentMapsBinding
    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    private val qrCodeScanner = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) {
        val client = getClient(Builder().setBarcodeFormats(FORMAT_QR_CODE).build())
        client.process(it, 0).addOnSuccessListener { results ->
            Log.d(TAG, results.firstOrNull()?.rawValue ?: "None")
            requireContext().run {
                val intent = Intent(this, ScooterService::class.java).apply {
                    putExtra("command", "start")
                }
                startForegroundService(this, intent)

                parentFragmentManager
                    .beginTransaction()
                    .replace(fragment_container_view_tag, ActiveRideFragment())
                    .commit()
            }
        }
    }

    /**
     * A set of private constants used in this class.
     */
    companion object {
        private val TAG = MapsFragment::class.qualifiedName
    }

    /**
     * Adds markers to the map based on location of scooters
     */
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        // Add a marker in ITU and move the camera
        val itu = LatLng(55.6596, 12.5910)
        Firebase.database(DATABASE_URL).reference.apply {
            keepSynced(true)
        }.child("scooters").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    //var tempScooter2 = postSnapshot.getValue<Scooter>()
                    val tempScooter = postSnapshot.getValue(Scooter::class.java)
                    var tempPosition =
                        tempScooter?.let { LatLng(it.getLat(), tempScooter.getLon()) }
                    if (tempScooter != null) {
                        tempPosition?.let {
                            MarkerOptions()
                                .position(it)
                                .title(tempScooter.getScooterName().toString())
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

        with(mapsBinding) {
            fab.setOnClickListener(this@MapsFragment)
        }

        //Floating action button appears when marker is selected
        googleMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                Log.d(TAG, marker.toString())
                with(mapsBinding) {
                    fab.show()
                }
            } else {
                marker.showInfoWindow()
                with(mapsBinding) {
                    fab.show()
                }
                Log.d(TAG, marker.toString() + "helo")
            }
            true
        }

        //Map is focused at ITU when opened
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(itu, 13f))
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
    ) = FragmentMapsBinding.inflate(inflater, container, false).let {
        mapsBinding = it
        it.root
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

    //Launches the QR-code scanner
    override fun onClick(ignored: View?) {
        qrCodeScanner.launch(null)
    }

    private fun addRide() {
        //Connect to database
        database = Firebase.database(DATABASE_URL).reference
        //Create search query
        val query = database.child("rides")
            .child(auth.currentUser?.uid ?: "None")
            .orderByChild("startTime")
        // Execute a query in the database to fetch appropriate data.
        val options = FirebaseRecyclerOptions.Builder<Rides>()
            .setQuery(query, Rides::class.java)
            .setLifecycleOwner(this)
            .build()
    }
}