package dk.itu.moapd.scootersharing.interfaces

import dk.itu.moapd.scootersharing.models.Rides
import dk.itu.moapd.scootersharing.models.Scooter

/**
 * An interface to implements listener methods for `RecyclerView` items.
 */
interface ItemClickListener  {

    fun onItemClickListener(rides: Rides, position: Int)

}