package dk.itu.moapd.scootersharing

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Scooter(var name : String, var where : String, var timestamp : Long) {

    fun getData() : String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    override fun toString() : String {
        return "$name is placed at $where"
    }

}