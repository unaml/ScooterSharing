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
        var updatedAt: Long? = null,
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
                "updatedAt" to updatedAt
            )
        }

    }