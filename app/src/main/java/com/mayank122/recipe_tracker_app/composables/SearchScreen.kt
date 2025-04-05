package com.mayank122.recipe_tracker_app.composables

import android.R.attr.category
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mayank122.recipe_tracker_app.data.MealDatabase
import com.mayank122.recipe_tracker_app.viewmodels.SearchMealViewModel
import com.mayank122.recipe_tracker_app.viewmodels.SearchMealViewModel.SearchMealViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SearchScreen(
    navController: NavHostController, viewModel: SearchMealViewModel = viewModel(
        factory = SearchMealViewModelFactory(
            MealDatabase.getDatabase(LocalContext.current).bookmarkDao()
        )
    )
) {

    var categoryToFetch by remember { mutableStateOf("") } // State for category
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = categoryToFetch) {
        viewModel.fetchMeals(categoryToFetch)
    }


    val meals by viewModel.meals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val bookmarkedIds by viewModel.bookmarkedMealIds.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(title = { Text("Search Meals") })
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            SearchBarView(
                textState = searchQuery,
                onTextChange = { newText -> searchQuery = newText },
                onSearchClick = {
                    categoryToFetch = searchQuery.text  // Update category when icon is clicked
                }

            )


            if (isLoading) {
                CenteredLoadingIndicator()
            } else if (error != null) {
                Text("Error: $error")
            } else {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),

                    modifier = Modifier.fillMaxSize(),

                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(meals, key = { it.idMeal }) { meal ->

                        val isBookmarked = bookmarkedIds.contains(meal.idMeal)

                        MealItem(meal = meal, isBookmarked = isBookmarked, onItemClick = { mealId ->

                            navController.navigate("detail/$mealId")
                        }, onBookmarkClick = { mealToToggle ->

                            viewModel.toggleBookmark(mealToToggle)
                        })
                    }
                }
            }
        }
    }


}