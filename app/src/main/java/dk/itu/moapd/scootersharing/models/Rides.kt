package dk.itu.moapd.scootersharing.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
open class Rides(
    var ride: Scooter? = null,
    val price: Double = 0.4,
    val startTime: Long? = null,
    val endTime: Long? = null
) {

    /**
     * Convert a instance of `Scooter' class into a `Map` to update the database on the RealTime
     * Firebase.
     *
     * @return A map with the table column name as the `key` and the class attribute as the `value`.
     */
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "ride" to ride,
            "price" to price,
            "startTime" to startTime,
            "endTime" to endTime
        )
    }

    @Exclude
    fun getInfo(): Scooter {
        return ride!!
    }

}