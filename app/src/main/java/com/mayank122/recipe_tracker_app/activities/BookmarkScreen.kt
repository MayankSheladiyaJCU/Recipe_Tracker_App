package com.mayank122.recipe_tracker_app.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.room.util.copy
import coil.compose.AsyncImage
import com.mayank122.recipe_tracker_app.R
import com.mayank122.recipe_tracker_app.data.BookmarkedMeal
import com.mayank122.recipe_tracker_app.data.MealDatabase
import com.mayank122.recipe_tracker_app.viewmodels.BookmarkViewModel
import com.mayank122.recipe_tracker_app.viewmodels.BookmarkViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavHostController,
    viewModel: BookmarkViewModel = viewModel(
        factory = BookmarkViewModelFactory(
            MealDatabase.getDatabase(LocalContext.current).bookmarkDao()
        )
    )
) {
    val bookmarks by viewModel.bookmarkedMeals.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bookmarked Meals") })

        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = innerPadding.calculateBottomPadding())
                 // ✅ Ensures content is above navigation bar

        ) {

            if (bookmarks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(bottom = 65.dp), // ✅ Ensure grid items are not hidden

                    contentAlignment = Alignment.Center
                ) {
                    Text("You haven't bookmarked any meals yet.", textAlign = TextAlign.Center)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        , // ✅ Ensure grid items are not hidden

                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bookmarks, key = { it.idMeal }) { bookmarkedMeal ->
                        // You can reuse MealItem or create a specific BookmarkedMealItem
                        // Here, we adapt MealItem's concept:
                        BookmarkedMealGridItem( // Use a dedicated item or adapt MealItem
                            bookmarkedMeal = bookmarkedMeal,
                            onItemClick = { mealId ->
                                navController.navigate("detail/$mealId")
                            },
                            onRemoveClick = { mealIdToRemove ->
                                // Call ViewModel to remove the bookmark
                                viewModel.removeBookmark(mealIdToRemove)
                            }
                        )
                    }
                }
            }

        }


    }
}


// Example dedicated Composable for the bookmark list item (adapting MealItem's structure)
@Composable
fun BookmarkedMealGridItem(
    bookmarkedMeal: BookmarkedMeal,
    onItemClick: (mealId: String) -> Unit,
    onRemoveClick: (mealId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(bookmarkedMeal.idMeal) }, // Click card to navigate
    ) { // Use Column to stack image, text, and remove button
        Box { // Box for image and overlay icon
            AsyncImage(
                model = bookmarkedMeal.strMealThumb,
                contentDescription = bookmarkedMeal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f) // Consistent aspect ratio
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            // Optional: Add a small remove icon button directly on the image
            IconButton(
                onClick = { onRemoveClick(bookmarkedMeal.idMeal) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bookmark_fill),
                    contentDescription = "Remove Bookmark",
                    tint = MaterialTheme.colorScheme.primary // Or choose another color
                )
            }
        }

        Text(
            text = bookmarkedMeal.strMeal ?: "No Name",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp) // Padding for text
        )

    }
}
