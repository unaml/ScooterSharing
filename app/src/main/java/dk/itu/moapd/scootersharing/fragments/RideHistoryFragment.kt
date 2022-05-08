package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import dk.itu.moapd.scootersharing.databinding.FragmentRideHistoryBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RideHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "RideHistoryFragment"
class RideHistoryFragment : Fragment() {

    private var _binding : FragmentRideHistoryBinding? = null
    //GUI variables
    private lateinit var infoText : EditText
    private lateinit var updateButton : Button
    private lateinit var nameText : TextView
    private lateinit var whereText : TextView

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

    companion object {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ride_history, container, false)

        //Binding between layout and fragment
        _binding = FragmentRideHistoryBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}