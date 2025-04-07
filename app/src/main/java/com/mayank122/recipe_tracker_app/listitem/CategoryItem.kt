package com.mayank122.recipe_tracker_app.listitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Display Category Item
@Composable
fun CategoryItem(name: String, imageRes: Int, onClick: () -> Unit) {
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
            .clickable { onClick() } // Trigger the callback on click
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
