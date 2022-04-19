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
import dk.itu.moapd.scootersharing.models.RidesDB
import dk.itu.moapd.scootersharing.models.Scooter
import dk.itu.moapd.scootersharing.activities.ScooterSharingActivity
import dk.itu.moapd.scootersharing.databinding.FragmentEditRideBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EditRideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "EditRideFragment"
class EditRideFragment : Fragment() {

    private var _binding : FragmentEditRideBinding? = null
    //GUI variables
    private lateinit var infoText : EditText
    private lateinit var updateButton : Button
    private lateinit var nameText : TextView
    private lateinit var whereText : TextView

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

    private val scooter : Scooter = Scooter ("", "", 0)
    companion object {
        lateinit var ridesDB : RidesDB
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_ride, container, false)

        //Binding between layout and fragment
        _binding = FragmentEditRideBinding.inflate(layoutInflater)

        return binding.root
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
            //Update button
            updateButton.setOnClickListener{
                if(nameText.text.isNotEmpty() && whereText.text.isNotEmpty()){
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