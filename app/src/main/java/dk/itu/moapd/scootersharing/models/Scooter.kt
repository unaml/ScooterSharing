package dk.itu.moapd.scootersharing.models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

    @IgnoreExtraProperties
    open class Scooter(
        var name: String? = null,
        val createdAt: Long? = null,
        val batteryLevel: Int = 100,
        val locked: Boolean = true,
        var updatedAt: Long? = null,
        var latitude: Double? = null,
        var longitude: Double? = null

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
                "name" to name,
                "createdAt" to createdAt,
                "updatedAt" to updatedAt,
                "batteryLevel" to batteryLevel,
                "locked" to locked,
                "latitude" to latitude,
                "longitude" to longitude
            )
        }

        @Exclude
        fun getLat(): Double {
            return latitude!!
        }

        @Exclude
        fun getLon(): Double {
            return longitude!!
        }

        @Exclude
        fun getScooterName(): String? {
            return name
        }

        @Exclude
        fun getCreated(): Long? {
            return createdAt
        }
        @Exclude
        fun getUpdated(): Long? {
            return updatedAt
        }




    }