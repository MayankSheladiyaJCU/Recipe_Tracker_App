package com.mayank122.recipe_tracker_app.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mayank122.recipe_tracker_app.activities.DetailScreen
import com.mayank122.recipe_tracker_app.activities.BookmarkScreen
import com.mayank122.recipe_tracker_app.activities.MealDetailScreen
import com.mayank122.recipe_tracker_app.activities.MealScreen
import com.mayank122.recipe_tracker_app.activities.RecipesHomeScreen
import com.mayank122.recipe_tracker_app.nav.NavItem
import com.mayank122.recipe_tracker_app.activities.CategoryScreen

@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { RecipesHomeScreen(navController) }
        composable(NavItem.Favorite.path) { BookmarkScreen(navController) }
        composable(NavItem.Items.path) { CategoryScreen(navController) }
        composable("detail/{id}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("id")
            DetailScreen(recipeId ?: "Unknown", navController)
        }
        composable("mealScreen/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            MealScreen(category = category, navController = navController)
        }

        composable("mealDetailScreen/{mealId}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("mealId") ?: ""
            MealDetailScreen(category = category, navController = navController)
        }

        composable("bookmarks") {
            BookmarkScreen(navController = navController)
        }
        composable("search") {
            SearchScreen(navController = navController)
        }

    }
}