package com.mayank122.recipe_tracker_app.model

data class MealResponse(
    val meals: List<Meal>
)

data class Meal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)
