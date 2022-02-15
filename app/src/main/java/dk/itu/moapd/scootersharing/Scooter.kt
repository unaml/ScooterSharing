package dk.itu.moapd.scootersharing

import java.sql.Timestamp
import java.util.*

class Scooter(_name : String, var where : String, var timestamp : Long) {

    var name = _name
    get() = field.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    set(value) {
        field = value.trim()
    }

    constructor(name : String) : this(name, where="ITU", timestamp=System.currentTimeMillis()) {timestamp.toString()}

    override fun toString() : String {
        return "$name is placed at $where"
    }

}