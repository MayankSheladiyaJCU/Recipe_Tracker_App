package com.mayank122.recipe_tracker_app.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mayank122.recipe_tracker_app.CookingTipsSection
import com.mayank122.recipe_tracker_app.HeadingSection
import com.mayank122.recipe_tracker_app.PopularCategorySection
import com.mayank122.recipe_tracker_app.SearchBar
import com.mayank122.recipe_tracker_app.TrendingSection

@Composable
fun RecipesHomeScreen(navController: NavHostController) {

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Remaining content below the top bar will scroll
            HeadingSection(text = "Find the Best Recipe for Cooking")
            SearchBar(navController) // Adding Search Bar
            PopularCategorySection(navController) // Popular Category Section
            TrendingSection(navController) // Trending Section
            CookingTipsSection(navController) // Cooking Tips Section

        }

    }
}

