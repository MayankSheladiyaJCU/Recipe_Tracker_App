package com.mayank122.recipe_tracker_app.model

sealed interface SaveableMeal {

    val id: String
    val isSaved: Boolean
}