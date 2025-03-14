package com.mayank122.recipe_tracker_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.mayank122.recipe_tracker_app.ui.theme.Recipe_Tracker_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Recipe_Tracker_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Recipe_main(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Recipe_main(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(title = "Recipe Tracker")

        // Small Heading Section
        HeadingSection(text = "Find the Best Recipe for Cooking")

        SearchBar() // Adding Search Bar


        PopularCategorySection() // Popular Category Section

    }
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White, // Background color for TextField
            focusedBorderColor = Color(0xFF6200EE), // Border color when focused
            unfocusedBorderColor = Color.Gray // Border color when unfocused
        )
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


@Composable
fun CategoryItem(name: String, imageRes: Int) {
    // Wrap image and text inside a Column with border
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(end = 10.dp)
            .border(
                width = 0.5.dp, // Border thickness
                color = Color.Black, // Border color
                shape = RoundedCornerShape(16.dp) // Rounded corners for the entire container
            )
            .padding(8.dp) // Padding inside the border
    ) {
        // Image for the category
        Image(
            painter = painterResource(id = imageRes), // Image from the drawable resource
            contentDescription = "$name Icon",
            modifier = Modifier
                .size(60.dp) // Size of the image
//             //   .background(Color.Gray, shape = RoundedCornerShape(10.dp)) // Circular image
        )
        Spacer(modifier = Modifier.height(12.dp)) // Space between image and text
        // Text below the image
        Text(
            text = name, // Category name
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Recipe_Tracker_AppTheme {
        Recipe_main("Android")
    }
}