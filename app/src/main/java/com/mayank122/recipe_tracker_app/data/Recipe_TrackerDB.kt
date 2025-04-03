package com.mayank122.recipe_tracker_app.data



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mayank122.recipe_tracker_app.data.Recipe_TrackerDao
import com.mayank122.recipe_tracker_app.data.User



//Room Database: Create the database instance.

@Database(entities = [User::class], version = 1, exportSchema = false)

abstract class Recipe_TrackerDB : RoomDatabase() {
    abstract fun Recipe_TrackerDao(): Recipe_TrackerDao

    //a companion object is similar to Java static declarations.
    //adding companion to the object declaration allows for adding
    // the "static" functionality to an object
    // used to create singleton object

    companion object {

        @Volatile
        private var INSTANCE: Recipe_TrackerDB? = null
        fun getDatabase(context: Context): Recipe_TrackerDB {
            //?: takes the right-hand value if the left-hand value is null (the elvis operator)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Recipe_TrackerDB::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}