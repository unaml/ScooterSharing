package dk.itu.moapd.scootersharing

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.databinding.ActivityScooterSharingBinding
import org.w3c.dom.Text

/** A class to customize an adapter with a 'ViewHolder' to populate a dumme dataset into a 'ListView' */
class ScooterArrayAdapter(context: Context, private var resource: Int, data: List<Scooter>) :
    ArrayAdapter<Scooter>(context, R.layout.list_rides, data)  {

        //A set of private constants used in this class
        companion object {
            private val TAG = ScooterArrayAdapter::class.qualifiedName
        }

    /**
    * An internal view holder class used to represent the layout that shows a single `RidesDB`
    * instance in the `ListView`.
    */
    private class ViewHolder(view: View) {
        val title : TextView = view.findViewById(R.id.title)
        val where : TextView = view.findViewById(R.id.where)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder : ViewHolder

        // The old view should be reused, if possible. You should check that this view is non-null
        // and of an appropriate type before using.
        if(view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
        } else
            viewHolder = view.tag as ViewHolder

        //Get the selected data in the dataset
        val scooter = getItem(position)
        Log.i(TAG, "Populate an item at position: $position")

        // Populate the view holder with the selected `DummyModel` data.
        viewHolder.title.text = scooter?.name
        viewHolder.where.text = scooter?.where

        view?.tag = viewHolder
        return view!!
    }

}