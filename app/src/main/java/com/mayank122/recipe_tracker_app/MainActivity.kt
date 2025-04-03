package com.mayank122.recipe_tracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.mayank122.recipe_tracker_app.ui.theme.Recipe_Tracker_AppTheme
import com.mayank122.recipe_tracker_app.viewmodels.Recipe_Tracker_ViewModel
import com.mayank122.recipe_tracker_app.views.AppNavigation

class MainActivity : ComponentActivity() {
    private  val Recipe_Tracker_ViewModel:Recipe_Tracker_ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Assuming this is for edge-to-edge UI
        setContent {
            Recipe_Tracker_AppTheme {
                AppNavigation()
            }
        }
    }
}

val icons = listOf(
    Icons.Default.Home,
    Icons.Default.Favorite,
    Icons.Default.Person
)

val items = listOf("Home", "Favorite", "Profile")



// Bottom Navigation
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf("home", "details")
    val icons = listOf(Icons.Default.Home, Icons.Default.Info)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = false,
                onClick = { navController.navigate(item) }
            )
        }
    }
}

@Composable
fun Recipe_main(name: String, modifier: Modifier = Modifier) {
    // Scaffold automatically sticks TopBar to the top
    Scaffold(
        topBar = {
            TopBar(title = "Recipe Tracker") // The top bar will be sticky here
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Inner padding for scaffold content area
                    .verticalScroll(rememberScrollState()) // This makes the content below scrollable
            ) {
                // Remaining content below the top bar will scroll
                HeadingSection(text = "Find the Best Recipe for Cooking")
                SearchBar() // Adding Search Bar
                PopularCategorySection() // Popular Category Section
                TrendingSection() // Trending Section
                CookingTipsSection() // Cooking Tips Section
            }
        }
    )
}


@Composable
fun TopBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6200EE)) // Purple color background
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            color = Color.White, // Set text color to white for contrast
            modifier = Modifier.weight(1f)
        )
    }
}


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var textState = TextFieldValue("") // Initial empty text

    OutlinedTextField(
        value = textState,
        onValueChange = { newText -> textState = newText }, // Update state on change
        label = { Text("Search Recipes") }, // Label for search bar
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, // Material Icon for search
                contentDescription = "Search Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth() // Take full width
            .padding(16.dp), // Padding around search bar
        shape = RoundedCornerShape(16.dp), // Apply rounded corners with 16dp radius
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            containerColor = Color.White, // Background color for TextField
//            focusedBorderColor = Color(0xFF6200EE), // Border color when focused
//            unfocusedBorderColor = Color.Gray // Border color when unfocused
//        )
    )
}

@Composable
fun PopularCategorySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Popular Categories",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Row for popular categories with horizontal scrolling
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()) // Enable horizontal scroll
                .padding(bottom = 16.dp)
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category 1: Italian
            CategoryItem(name = "Italian", imageRes = R.drawable.dimsum)

            // Category 2: Chinese
            CategoryItem(name = "Chinese", imageRes = R.drawable.spaghetti)

            // Category 3: Indian
            CategoryItem(name = "Indian", imageRes = R.drawable.samosa)

            // Category 4: Nepali
            CategoryItem(name = "Nepali", imageRes = R.drawable.nepali)

            // Category 5: Mexican
            CategoryItem(name = "Mexican", imageRes = R.drawable.mexican)

            // Category 6: American
            CategoryItem(name = "American", imageRes = R.drawable.american)

            // Category 7: Thai
            CategoryItem(name = "Thai", imageRes = R.drawable.thaifood)

            // Category 8: Japanese
            CategoryItem(name = "Japanese", imageRes = R.drawable.japanese)
        }
    }
}


//@Composable
//fun CategoryItem(name: String, imageRes: Int) {
//    // Wrap image and text inside a Column with border
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .width(100.dp)
//            .padding(end = 10.dp)
//            .border(
//                width = 0.5.dp, // Border thickness
//                color = Color.Black, // Border color
//                shape = RoundedCornerShape(16.dp) // Rounded corners for the entire container
//            )
//            .padding(8.dp) // Padding inside the border
//    ) {
//        // Image for the category
//        Image(
//            painter = painterResource(id = imageRes), // Image from the drawable resource
//            contentDescription = "$name Icon",
//            modifier = Modifier
//                .size(60.dp) // Size of the image
////             //   .background(Color.Gray, shape = RoundedCornerShape(10.dp)) // Circular image
//        )
//        Spacer(modifier = Modifier.height(12.dp)) // Space between image and text
//        // Text below the image
//        Text(
//            text = name, // Category name
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Normal,
//            color = Color.Black
//        )
//    }
//}

@Composable
fun TrendingSection() {
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
                TrendingItem(recipe)
            }
        }
    }
}

// UI function for each trending item
@Composable
fun TrendingItem(recipe: TrendingRecipe) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(240.dp) // Large image width
            .height(240.dp) // Height for the container
    ) {
        Box {
            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = recipe.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = recipe.restaurant,
                        fontSize = 15.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

// 🔹 Data function (Fixed Drawable References)
fun getTrendingRecipes(): List<TrendingRecipe> {
    return listOf(
        TrendingRecipe("Sushi", "Tokyo Diner", R.drawable.r1),
        TrendingRecipe("Burger", "Fast Bites", R.drawable.r2),
        TrendingRecipe("Rice", "Fast Bites", R.drawable.r3)

    )
}

// 🔹 Data Model (with correct annotation)
data class TrendingRecipe(
    val name: String,
    val restaurant: String,
    @DrawableRes val imageRes: Int
)


@Composable
fun CookingTipsSection() {
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
                .horizontalScroll(rememberScrollState()) // Enables horizontal scrolling
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tips = getCookingTips()
            tips.forEach { tip ->
                CookingTipCard(tip)
            }
        }
    }
}

@Composable
fun CookingTipCard(tip: CookingTip) {
    Card(
        modifier = Modifier
            .width(200.dp) // Set a fixed width for each tip card
            .padding(end = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = tip.imageRes),
                contentDescription = tip.title,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tip.shortDescription,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Data Class for Cooking Tips
data class CookingTip(val title: String, val shortDescription: String, val imageRes: Int)

// Sample Function to Get Tips
fun getCookingTips(): List<CookingTip> {
    return listOf(
        CookingTip("How to Chop Onions", "Avoid tears with this simple trick!", R.drawable.r1),
        CookingTip("Perfect Boiled Eggs", "Get the perfect texture every time.", R.drawable.r2)
    )
}


// Preview with Navigation
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Recipe_Tracker_AppTheme {
        AppNavigation()
    }
}