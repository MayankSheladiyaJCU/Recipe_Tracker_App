package com.mayank122.recipe_tracker_app.model

import java.io.Serializable

data class IngredientModel(
    val name: String,
    val quantity: String
): Serializable