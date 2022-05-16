package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dk.itu.moapd.scootersharing.databinding.FragmentLocationBinding
import dk.itu.moapd.scootersharing.models.ScooterSharingVM
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "LocationFragment"

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

    /**
     * This view model helper class for the UI controller is responsible for preparing data from the
     * location-aware service.
     */
    private val viewModel: ScooterSharingVM by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Define the UI components listeners.
        with (binding) {
            viewModel.locationState.observe(viewLifecycleOwner) { location ->
                latitudeTextField.editText?.setText(location.latitude.toString())
                longitudeTextField.editText?.setText(location.longitude.toString())
                timeTextField.editText?.setText(location.time.toDateString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Return the timestamp as a `String`.
     *
     * @return The timestamp formatted as a `String` using the default locale.
     */
    private fun Long.toDateString() : String {
        val date = Date(this)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

}
