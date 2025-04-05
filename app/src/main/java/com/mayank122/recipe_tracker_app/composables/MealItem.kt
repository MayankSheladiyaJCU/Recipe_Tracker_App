package com.mayank122.recipe_tracker_app.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mayank122.recipe_tracker_app.R
import com.mayank122.recipe_tracker_app.model.Meal

@Composable
fun MealItem(
    meal: Meal,
    isBookmarked: Boolean,

    onItemClick: (mealId: String) -> Unit,
    onBookmarkClick: (meal: Meal) -> Unit

) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                onItemClick(meal.idMeal)
            }

    ) {
        Box {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { onBookmarkClick(meal) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {

                Icon(
                    imageVector = if (isBookmarked) ImageVector.vectorResource(R.drawable.ic_bookmark_fill) else ImageVector.vectorResource(
                        R.drawable.ic_bookmark
                    ),
                    contentDescription = if (isBookmarked) "Remove Bookmark" else "Add Bookmark",
                    tint = if (isBookmarked) MaterialTheme.colorScheme.primary else Color.Gray

                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = meal.strMeal,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
    }
}
