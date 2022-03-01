package dk.itu.moapd.scootersharing
import android.content.Context
import java.util.Random
import kotlin.collections.ArrayList

class RidesDB private constructor (context : Context) {

    private val rides = ArrayList <Scooter>()
    private var lastScooter = Scooter ("", "", 0)

    companion object : RidesDBHolder <RidesDB, Context>(::RidesDB)
    init {
        rides.add(
            Scooter("Chuck Norris", "ITU", randomDate())
        )
        rides.add(
            Scooter("Bruce Lee", "Fields", randomDate())
        )
        rides.add (
            Scooter ("Rambo", "Københavns Lufthavn", randomDate())
        )
        rides.add (
            Scooter ("Fabricio", "Nørrebrogade", randomDate())
        )
        rides.add (
            Scooter ("Maggy Juicy", "Øresundskollegiet", randomDate())
        )
    }
    fun getScooters(): List<Scooter> {
        print(rides)
        return rides
    }
    fun addScooter(name: String, where: String) {
        val last = Scooter(name, where, timestamp = System.currentTimeMillis())
        rides.add(last)
        lastScooter = last
        print(last)
    }
    fun updateScooter(name: String, where: String) {
        lastScooter.name = name
        lastScooter.where = where
        lastScooter.timestamp = System.currentTimeMillis()
    }
    fun getLastScooterInfo(): String {
        return "Name: ${lastScooter.name}, where: ${lastScooter.where}, time: ${lastScooter.timestamp}"
    }
    /**
     * Get the current timestamp and generate a random data in the
    ,→ last 365 days .
     *
     * @return A random timestamp in the last year .
     */
    private fun randomDate(): Long {
        val random = Random()
        val now = System.currentTimeMillis()
        val year = random.nextDouble() * 1000 * 60 * 60 * 24 * 365
        return (now - year).toLong()
    }
}
open class RidesDBHolder <out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null
    fun get (arg: A): T {
        val checkInstance = instance
        if (checkInstance != null)
            return checkInstance
        return synchronized (this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null)
                checkInstanceAgain
            else {
                val created = creator !!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}