package com.mayank122.recipe_tracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mayank122.recipe_tracker_app.composables.MainScreen
import com.mayank122.recipe_tracker_app.data.utils.categories
import com.mayank122.recipe_tracker_app.data.utils.getCookingTips
import com.mayank122.recipe_tracker_app.data.utils.getTrendingRecipes
import com.mayank122.recipe_tracker_app.listitem.CategoryItem
import com.mayank122.recipe_tracker_app.listitem.CookingTipCard
import com.mayank122.recipe_tracker_app.listitem.TrendingItem
import com.mayank122.recipe_tracker_app.ui.theme.Recipe_Tracker_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Assuming this is for edge-to-edge UI
        setContent {
            Recipe_Tracker_AppTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(navController = navController)
                }
            }
        }
    }
}

// heading section
@Composable
fun HeadingSection(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE),
            lineHeight = 35.sp,
            modifier = Modifier.fillMaxWidth() // Ensures it takes full width
        )
    }
}

// Search bar code
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavHostController) {
    var textState = TextFieldValue("") // Initial empty text

    OutlinedTextField(
        value = textState,
        onValueChange = { newText -> textState = newText },
        label = { Text("Search Recipes") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        },
        readOnly = true,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable{
                navController.navigate("search")
            },
        shape = RoundedCornerShape(16.dp),
    )
}

// Popular category
@Composable
fun PopularCategorySection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, start = 16.dp, top = 16.dp)
    ) {
        Text(
            text = "Popular Categories",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CategoryRow { category ->
            navController.navigate("mealScreen/${category}") // Update selected category on click
        }

    }
}

//Category display
@Composable
fun CategoryRow(onCategorySelected: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState) // Horizontal scroll
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        categories.forEach { category ->
            CategoryItem(
                name = category.name,
                imageRes = category.imageRes,
                onClick = { onCategorySelected(category.name) } // Pass the category name
            )
        }
    }
}

// Trending section
@Composable
fun TrendingSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Trending Recipes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            getTrendingRecipes().map { recipe ->
                TrendingItem(recipe, navController)
            }
        }
    }
}

// Cooking Tip Section
@Composable
fun CookingTipsSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(bottom = 80.dp)
    ) {
        Text(
            text = "Cooking Tips & Tricks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tips = getCookingTips()
            tips.forEach { tip ->
                CookingTipCard(tip, navController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()

    Recipe_Tracker_AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen(navController = navController)
        }
    }
}