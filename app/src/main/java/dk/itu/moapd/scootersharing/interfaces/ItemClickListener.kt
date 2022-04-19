package dk.itu.moapd.scootersharing.interfaces

import dk.itu.moapd.scootersharing.models.Scooter

/**
 * An interface to implements listener methods for `RecyclerView` items.
 */
interface ItemClickListener  {

    /**
     * Implement this method to be executed when the user press an item in the `RecyclerView` for a
     * long time.
     *
     * @param dummy An instance of `Dummy` class.
     * @param position The position of the item selected in the `RecyclerView`.
     */
    fun onItemClickListener(scooter: Scooter, position: Int)

}