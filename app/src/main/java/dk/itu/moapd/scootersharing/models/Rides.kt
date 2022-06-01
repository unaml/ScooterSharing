package dk.itu.moapd.scootersharing.models

import com.google.firebase.database.Exclude

data class Rides(
    var scooter: String = "Unknown",
    val price: Double = 0.4,
    val startTime: Long? = null,
    val endTime: Long? = null
) {

}