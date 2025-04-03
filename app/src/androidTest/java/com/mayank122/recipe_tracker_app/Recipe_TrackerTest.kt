package  com.mayank122.recipe_tracker_app
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mayank122.recipe_tracker_app.data.User
import com.mayank122.recipe_tracker_app.data.Recipe_TrackerDB
import com.mayank122.recipe_tracker_app.data.Recipe_TrackerDao

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Recipe_TrackerTest {
    private lateinit var db: Recipe_TrackerDB

    private lateinit var Recipe_TrackerDao: Recipe_TrackerDao

    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before

    fun createDb() {

        // Using an in-memory database because the information stored here disappears when the

        // process is killed.

        db = Room.inMemoryDatabaseBuilder(appContext, Recipe_TrackerDB::class.java)

            // Allowing main thread queries, just for testing.

            .allowMainThreadQueries()

            .build()

        Recipe_TrackerDao = db.Recipe_TrackerDao()

    }

    @After

    fun closeDb() {

        db.close()

    }

    @Test
    fun useAppContext() {

        assertEquals("com.mayank122.recipe_tracker_app", appContext.packageName)
    }

    @Test

    @Throws(Exception::class)

    //runBlocking:

    //- Blocks the current thread until the coroutine completes.

    //- Used to bridge synchronous and asynchronous code.

    //- Creates a scope for coroutines, controlling their lifetime.

    //Key differences:

    //- suspend defines asynchronous functions.

    //- runBlocking executes coroutines synchronously.

    fun insertAndGetUser() = runBlocking {

        val user = User(username = "Tony", score = 100, duration = 50, date = 10000L)

        Recipe_TrackerDao.insert(user)

        val fetchedUser = Recipe_TrackerDao.getUserById(1)

        assertEquals("Tony", fetchedUser?.username)

    }

    @Test

    fun deleteAllUsers() = runBlocking {

        val user = User(username = "Sarah", score = 80, duration = 70)

        Recipe_TrackerDao.insert(user)

        Recipe_TrackerDao.deleteAllUsers()

        val fetchedUser = Recipe_TrackerDao.getUserById(1)

        assertNull(fetchedUser)

    }

    @Test

    fun getAllUsers() = runBlocking {

        val user1 = User(username = "Sarah", score = 80, duration = 70)

        val user2 = User(username = "Tony", score = 100, duration = 50, date = 10000L)

        Recipe_TrackerDao.insert(user1)
        Recipe_TrackerDao.insert(user2)
        val allUsers = Recipe_TrackerDao.getAllUsers()

        assertEquals(allUsers.size, 2)

        //order by score DESC -> Tony is the first record

        assertEquals(allUsers[0].username, "Tony")

        assertEquals(allUsers[1].username, "Sarah")

    }
}