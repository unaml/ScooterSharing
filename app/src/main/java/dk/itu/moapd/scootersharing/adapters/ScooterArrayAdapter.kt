package dk.itu.moapd.scootersharing.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.interfaces.ItemClickListener
import dk.itu.moapd.scootersharing.models.Scooter

/** A class to customize an adapter with a 'ViewHolder' to populate a dummy dataset into a 'RecyclerView' */
class ScooterArrayAdapter(private val itemClickListener: ItemClickListener, val data: ArrayList<Scooter>) :
    RecyclerView.Adapter<ScooterArrayAdapter.ViewHolder>() {

    //A set of private constants used in this class
    companion object {
        private val TAG = ScooterArrayAdapter::class.qualifiedName
    }

    /**
     * An internal view holder class used to represent the layout that shows a single `RidesDB` instance
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val where : TextView = view.findViewById(R.id.where)
        val timeStamp : TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_rides, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dummy = data[position]
        Log.i(TAG, "Populate an item at position: $position")

        holder.apply {
            title.text = dummy?.name
            where.text = dummy?.where
            timeStamp.text = dummy.timestamp.toString()

            // Listen for long clicks in the current item.
            itemView.setOnLongClickListener {
                itemClickListener.onItemClickListener(dummy, position)
                true
            }
        }
    }

    override fun getItemCount() = data.size

    /**
     * This method adds a new item to the dataset and updates the RecyclerView via a notification
     * observer.
     *
     * @param dummy The new item to be added to the dataset.
     */
    fun addItem(scooter: Scooter) {
        data.add(scooter)
        notifyItemInserted(data.size)
    }

    /**
     * This method removes an item from the dataset and updates the RecyclerView via a notification
     * observer.
     *
     * @param position The position of the item to be removed from the dataset.
     */
    fun removeAt(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

}