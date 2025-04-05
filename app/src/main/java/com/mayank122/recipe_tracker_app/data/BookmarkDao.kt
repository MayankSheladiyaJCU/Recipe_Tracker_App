package com.mayank122.recipe_tracker_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(meal: BookmarkedMeal)

    @Query("DELETE FROM bookmarked_meals WHERE idMeal = :mealId")
    suspend fun deleteBookmark(mealId: String)

    @Query("SELECT * FROM bookmarked_meals ORDER BY strMeal ASC")
    fun getAllBookmarks(): Flow<List<BookmarkedMeal>>

    @Query("SELECT idMeal FROM bookmarked_meals")
    fun getAllBookmarkIds(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_meals WHERE idMeal = :mealId LIMIT 1)")
    fun isBookmarked(mealId: String): Flow<Boolean>

    @Query("SELECT idMeal FROM bookmarked_meals")
    suspend fun getAllBookmarkIdsOnce(): List<String>

}
