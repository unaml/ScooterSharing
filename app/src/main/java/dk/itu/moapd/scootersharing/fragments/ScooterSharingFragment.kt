package dk.itu.moapd.scootersharing.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.RidesDB
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding


private const val TAG = "ScooterSharingFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [ScooterSharingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScooterSharingFragment : Fragment(){

        //View binding for ScooterSharingActivity
        private lateinit var binding : FragmentScooterSharingBinding
        private lateinit var ridesRecyclerView: RecyclerView
        private lateinit var adapter : ScooterArrayAdapter


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
            binding = FragmentScooterSharingBinding.inflate(layoutInflater)

            //Singleton to share an object between activites
            ridesDB = RidesDB.get(requireContext())
            val rides = ridesDB.getScooters()
            val fm = parentFragmentManager

            //ScooterSharingFragment.adapter = ScooterArrayAdapter(ridesDB.getScooters()) TODO: uncomment
            //(requireContext(), R.layout.list_rides, rides)

            with(binding){
                //Start button
                startButton.setOnClickListener{
                    //Start the application
                    fm
                        .beginTransaction()
                        .replace(R.id.fragment_container_view_tag, StartRideFragment())
                        .commit()
                    Log.d(TAG, "StartRide called")
                }
                //Edit button
                editButton.setOnClickListener{
                    //Edit ride
                    Log.d(TAG, "EditRide called")
                    fm
                        .beginTransaction()
                        .replace(R.id.fragment_container_view_tag, EditRideFragment())
                        .commit()
                }
                //List button
                listButton.setOnClickListener{
                    ridesRecyclerView.adapter = ScooterSharingFragment.adapter
                }
            }
            return (binding.root)
            }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            }
        }

