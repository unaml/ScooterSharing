package dk.itu.moapd.scootersharing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dk.itu.moapd.scootersharing.databinding.FragmentEditRideBinding
import dk.itu.moapd.scootersharing.databinding.FragmentStartRideBinding

private const val TAG = "StartRideActivity"
/**
 * A simple [Fragment] subclass.
 * Use the [StartRideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartRideFragment : Fragment() {
    private lateinit var binding: FragmentStartRideBinding

    //GUI variables
    private lateinit var infoText: EditText
    private lateinit var startButton: Button
    private lateinit var nameText: TextView
    private lateinit var whereText: TextView

    private val scooter: Scooter = Scooter("", "", 0)

    companion object {
        lateinit var ridesDB: RidesDB
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_edit_ride, container, false)
        //Edit texts
        //infoText = view.findViewById(R.id.info_text) //TODO : change r.id
        //whereText = view.findViewById(R.id.where_text)
        //nameText = view.findViewById(R.id.name_text)
        //startButton = view.findViewById(R.id.start_button)

        //Binding between layout and fragment
        binding = FragmentStartRideBinding.inflate(layoutInflater)

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
        // Inflate the layout for this fragment
        return (binding.root)
    }

    private fun updateUi () {
        infoText.setText(scooter.toString () )
    }
}
