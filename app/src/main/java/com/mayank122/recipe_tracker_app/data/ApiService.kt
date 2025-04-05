package com.mayank122.recipe_tracker_app.data

import com.mayank122.recipe_tracker_app.model.CategoryResponse
import com.mayank122.recipe_tracker_app.model.MealDetailResponse
import com.mayank122.recipe_tracker_app.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("filter.php")
    suspend fun getMeals(@Query("a") area: String): MealResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse

    @GET("filter.php")
    suspend fun queryMealsByCategory(@Query("c") category: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") mealId: String): MealDetailResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse


}
