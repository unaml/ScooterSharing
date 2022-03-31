package dk.itu.moapd.scootersharing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.models.Scooter

/** A class to customize an adapter with a 'ViewHolder' to populate a dummy dataset into a 'RecyclerView' */
class ScooterArrayAdapter(private val data: List<Scooter>, options: FirebaseRecyclerOptions<Scooter>) : FirebaseRecyclerAdapter<Scooter, ScooterArrayAdapter.ViewHolder>(options) {



    //A set of private constants used in this class
    companion object {
        private val TAG = ScooterArrayAdapter::class.qualifiedName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_rides, parent, false)
        return ViewHolder(view)
    }

    /**
     * An internal view holder class used to represent the layout that shows a single `RidesDB` instance
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val where : TextView = view.findViewById(R.id.where)
        val timeStamp : TextView = view.findViewById(R.id.date)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dummy = data[position]
        holder.apply {
            title.text = dummy?.name
            where.text = dummy?.where
            timeStamp.text = dummy.timestamp.toString()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Scooter) {
        TODO("Not yet implemented")
    }


}