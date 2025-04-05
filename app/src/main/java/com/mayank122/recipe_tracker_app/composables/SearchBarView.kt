package com.mayank122.recipe_tracker_app.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SearchBarView(
    textState: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    onSearchClick: () -> Unit // Add callback for icon click
) {
    OutlinedTextField(
        value = textState,
        onValueChange = onTextChange,
        label = { Text("Search Recipes") },
        trailingIcon = {
            IconButton(onClick = { onSearchClick() }) { // Trigger category update
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    )
}
