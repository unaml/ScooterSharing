package dk.itu.moapd.scootersharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding
private const val TAG = "ScooterSharingActivity"

class ScooterSharingActivity : AppCompatActivity() {

    //View binding for ScooterSharingActivity
    private lateinit var binding : ActivityScooterSharingBinding

    //  A set of static attributes used in this activity class.
    companion object {
        lateinit var ridesDB : RidesDB
        private lateinit var adapter : ScooterArrayAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        //Singleton to share an object between activites
        ridesDB = RidesDB.get(this)
        val rides = ridesDB.getScooters()

        adapter = ScooterArrayAdapter(this, R.layout.list_rides, rides)

        binding = ActivityScooterSharingBinding.inflate(layoutInflater)

        with(binding){
            //Start button
            startButton.setOnClickListener{
                //Start the application
                Log.d(TAG, "StartRide called")
                val intent = Intent(baseContext, StartRideActivity::class.java) //Change baseContext to requireContext later
                startActivity(intent)
            }
            //Edit button
            editButton.setOnClickListener{
                //Edit ride
                Log.d(TAG, "EditRide called")
                val intent = Intent(baseContext, EditRideActivity::class.java)
                startActivity(intent)
            }
            //List button
            listButton.setOnClickListener{
                ridesListView.adapter = adapter
            }
        }



        val view = binding.root
        setContentView(view)

    }

}