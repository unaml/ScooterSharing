package dk.itu.moapd.scootersharing.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.interfaces.ItemClickListener
import dk.itu.moapd.scootersharing.models.Rides
import java.text.SimpleDateFormat
import java.util.*

/** A class to customize an adapter with a 'ViewHolder' to populate a dummy dataset into a 'RecyclerView' */
class ScooterArrayAdapter(
    private val itemClickListener: ItemClickListener,
    options: FirebaseRecyclerOptions<Rides>
) :
    FirebaseRecyclerAdapter<Rides, ScooterArrayAdapter.ViewHolder>(options) {


    //A set of private constants used in this class
    companion object {
        private val TAG = ScooterArrayAdapter::class.qualifiedName
    }

    /**
     * An internal view holder class used to represent the layout that shows a single `RidesDB` instance
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.title)
        val rentedAt: TextView = view.findViewById(R.id.`when`)
        val price: TextView = view.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_ride_history_element, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, rides: Rides) {
        Log.i(TAG, "Populate an item at position: $position")

        holder.apply {
            name.text = "Scooter: ${rides.scooter}"
            rentedAt.text = "Date: " + rides.endTime?.toDateString()
            price.text = "Price: ${rides.price} DKK"

            itemView.setOnLongClickListener {
                itemClickListener.onItemClickListener(rides, position)
                true
            }
        }
    }
    private fun Long.toDateString() : String {
        val date = Date(this)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
