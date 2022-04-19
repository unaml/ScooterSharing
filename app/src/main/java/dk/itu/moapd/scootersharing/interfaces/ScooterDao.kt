package dk.itu.moapd.scootersharing.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import dk.itu.moapd.scootersharing.models.Scooter

@Dao
interface ScooterDao {

    @Insert
    fun insert(scooter : Scooter)

    @Update
    fun update(scooter: Scooter)

    @Delete
    fun delete(scooter: Scooter)

    @Query("SELECT * FROM scooter")
    fun getAll(): LiveData<List<Scooter>>

    @Query("SELECT * FROM scooter WHERE name LIKE :name")
    fun findByName(name: String): LiveData<Scooter?>
}