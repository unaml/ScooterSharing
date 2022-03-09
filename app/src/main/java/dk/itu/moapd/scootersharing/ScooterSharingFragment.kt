package dk.itu.moapd.scootersharing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding


private const val TAG = "ScooterSharingFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [ScooterSharingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScooterSharingFragment : Fragment() {

        //View binding for ScooterSharingActivity
        private lateinit var binding : FragmentScooterSharingBinding

        private lateinit var adapter : ScooterArrayAdapter
        private lateinit var titleField : EditText
        private lateinit var startButton : Button
        private lateinit var editButton : Button
        private lateinit var listButton : Button
        private lateinit var scooterList : ListView

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
            val view = inflater.inflate(R.layout.fragment_scooter_sharing, container, false)
            titleField = view.findViewById(R.id.scooter_title) as EditText
            startButton = view.findViewById(R.id.start_button) as Button
            editButton = view.findViewById(R.id.edit_button) as Button
            listButton = view.findViewById(R.id.list_button) as Button
            scooterList = view.findViewById(R.id.rides_list_view) as ListView


            //Singleton to share an object between activites
            ScooterSharingActivity.ridesDB = RidesDB.get(requireContext())
            val rides = ScooterSharingActivity.ridesDB.getScooters()

            ScooterSharingFragment.adapter = ScooterArrayAdapter(requireContext(), R.layout.list_rides, rides)

            with(binding){
                //Start button
                startButton.setOnClickListener{
                    //Start the application
                    Log.d(TAG, "StartRide called")
                    val intent = Intent(requireContext(), StartRideActivity::class.java) //Change baseContext to requireContext later
                    startActivity(intent)
                }
                //Edit button
                editButton.setOnClickListener{
                    //Edit ride
                    Log.d(TAG, "EditRide called")
                    val intent = Intent(requireContext(), EditRideActivity::class.java)
                    startActivity(intent)
                }
                //List button
                listButton.setOnClickListener{
                    ridesListView.adapter = ScooterSharingFragment.adapter
                }
            }

            return view
            }
        }

