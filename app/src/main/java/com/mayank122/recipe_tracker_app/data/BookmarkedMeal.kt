package com.mayank122.recipe_tracker_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mayank122.recipe_tracker_app.model.Meal

@Entity(tableName = "bookmarked_meals")
data class BookmarkedMeal(
    @PrimaryKey val idMeal: String,
    val strMeal: String?, // Include fields you want to store
    val strMealThumb: String?
    // Add other relevant fields if needed
)

fun Meal.toBookmarkedMeal(): BookmarkedMeal {
    return BookmarkedMeal(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strMealThumb = this.strMealThumb
        // map other fields
    )
}
