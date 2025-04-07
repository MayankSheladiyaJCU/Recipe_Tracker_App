package com.mayank122.recipe_tracker_app.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
// Bottom Navigation Bar
sealed class NavItem {
    object Home :
        Item(path = NavPath.HOME.toString(), title = NavTitle.HOME, icon = Icons.Default.Home)

    object Items :
        Item(path = NavPath.PROFILE.toString(), title = NavTitle.PROFILE, icon = Icons.Default.Menu)

    object Favorite :
        Item(path = NavPath.ITEMS.toString(), title = NavTitle.FAVORITE, icon = Icons.Default.Favorite)

}