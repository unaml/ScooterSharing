package dk.itu.moapd.scootersharing.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dk.itu.moapd.scootersharing.interfaces.ScooterDao

/**
 * A base class to provide direct access to the underlying the database implementation.
 */
@Database(entities = [Scooter::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Define an abstract method that has zero arguments and returns an instance of the `DummyDAO`
     * class.
     */
    abstract fun scooterDao(): ScooterDao

    /**
     * A set of static attributes and method used in this class.
     */
    companion object {

        /**
         * A singleton database instance to be used in the entire `Storage Room` application.
         */
        @Volatile
        private var INSTANCE : AppDatabase? = null

        /**
         * This method creates an `AppDatabase` singleton to prevent multiple instance of database
         * opening at the same time.
         *
         * @param context The application context.
         *
         * @return A singleton with the database instance.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database_name"
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }

    }

}