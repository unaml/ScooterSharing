package dk.itu.moapd.scootersharing.models
import android.content.Context
import java.util.Random
import kotlin.collections.ArrayList

class RidesDB private constructor (context : Context) {

    private val rides = ArrayList <Scooter>()
    private var lastScooter = Scooter ("",System.currentTimeMillis(), System.currentTimeMillis())

    companion object : RidesDBHolder<RidesDB, Context>(::RidesDB)
    init {
        rides.add(
            Scooter("hey", randomDate(), randomDate())
        )
        rides.add(
            Scooter("Bruce Lee")
        )
        rides.add (
            Scooter("Fabricio", randomDate(), randomDate())
        )
        rides.add (
            Scooter("Maggy", randomDate(), randomDate())
        )
        rides.add (
            Scooter("Juicy", randomDate(), randomDate())
        )
    }
    fun getScooters(): List<Scooter> {
        print(rides)
        return rides
    }

    fun deleteScooter(scooter: Scooter) {
        rides.remove(scooter)
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