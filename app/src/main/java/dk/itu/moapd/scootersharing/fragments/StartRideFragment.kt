package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dk.itu.moapd.scootersharing.RidesDB
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import dk.itu.moapd.scootersharing.databinding.FragmentEditRideBinding
import dk.itu.moapd.scootersharing.databinding.FragmentStartRideBinding
import dk.itu.moapd.scootersharing.models.Scooter

private const val TAG = "StartRideActivity"
/**
 * A simple [Fragment] subclass.
 * Use the [StartRideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartRideFragment : Fragment() {

    private var _binding: FragmentStartRideBinding? = null

    //GUI variables
    private lateinit var infoText: EditText
    private lateinit var startButton: Button
    private lateinit var nameText: TextView
    private lateinit var whereText: TextView

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

    private val scooter: Scooter = Scooter("", "", 0)

    companion object {
        lateinit var ridesDB: RidesDB
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

        //Singleton to share an object between activites
        ScooterSharingActivity.ridesDB = RidesDB.get(requireContext())
        val rides = ScooterSharingActivity.ridesDB.getScooters()

        with(binding) {

            startButton.setOnClickListener {
                if (nameText.text.isNotEmpty() && whereText.text.isNotEmpty()) {
                    //Update the object attributes
                    val name = nameText.text.toString().trim()
                    val where = whereText.text.toString().trim()
                    scooter.name = name
                    scooter.where = where
                    scooter.timestamp = System.currentTimeMillis()
                    //Reset
                    nameText.setText("")
                    whereText.setText("")
                    updateUi()
                }

            }

        }
    }

    private fun updateUi () {
        infoText.setText(scooter.toString () )
    }
}
