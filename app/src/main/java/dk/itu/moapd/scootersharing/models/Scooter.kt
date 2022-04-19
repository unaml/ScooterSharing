package dk.itu.moapd.scootersharing.models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scooter")
class Scooter(
    @PrimaryKey(autoGenerate = true)
    val uid : Int,
    @ColumnInfo(name = "name")
    var name : String?,
    @ColumnInfo(name = "where")
    var where : String?,
    @ColumnInfo(name = "timestamp")
    var timestamp : Long
) {

    fun getData() : String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    override fun toString() : String {
        return "$name is placed at $where"
    }

}