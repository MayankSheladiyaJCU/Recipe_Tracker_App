package com.mayank122.recipe_tracker_app.views

import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar


import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.w3c.dom.Text

// Sample Data Model
data class FavoriteItem(val id: Int, val name: String)

// ViewModel to manage favorites
class FavoriteViewModel : ViewModel() {
    private var _favorites = mutableStateListOf(
        FavoriteItem(1, "Pizza"),
        FavoriteItem(2, "Burger"),
        FavoriteItem(3, "Pasta"),
        FavoriteItem(4, "Ice Cream")
    )
    val favorites: List<FavoriteItem> get() = _favorites

    fun removeFromFavorites(item: FavoriteItem) {
        _favorites.remove(item)
    }
}


// UI for each favorite item
@Composable
fun FavoriteItemRow(item: FavoriteItem, onRemove: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onRemove() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.name, fontSize = 20.sp, modifier = Modifier.weight(1f))
            val button = Button(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        
        }
    }
}
