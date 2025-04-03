package com.mayank122.recipe_tracker_app.views

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.mayank122.recipe_tracker_app.BottomNavigationBar
import com.mayank122.recipe_tracker_app.R
import com.mayank122.recipe_tracker_app.Recipe_main
import com.mayank122.recipe_tracker_app.ui.theme.Recipe_Tracker_AppTheme
import java.nio.file.WatchEvent.Modifier

@Composable
fun AppNavigation()
{
    val navController =  rememberNavController()

    NavHost(navController, startDestination = "home"){
        composable("home") { HomeScreen(navController)  }
        composable("second") { ItemScrren(navController)  }

    }
}

// Navigation Setup
//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//
//    Recipe_Tracker_AppTheme {
//        Scaffold(
//            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
//            bottomBar = { BottomNavigationBar(navController) }
//        ) { innerPadding ->
//            NavHost(
//                navController = navController,
//                startDestination = "home",
//                modifier = androidx.compose.ui.Modifier.padding(innerPadding)
//            ) {
//                composable("home") { Recipe_main("Android", navController) }
//                composable("details") { ItemScreen(navController) }
//            }
//        }
//    }
//}

//@Composable
//fun HomeScreen(navController : NavController, modifier: Modifier = Modifier)
//{
//    val context = LocalContext.current
//    val marimbaSong = MediaPlayer.create(context, R.raw.marimba)
//    val merengueSong = MediaPlayer.create(context, R.raw.merengue)


//    Scaffold(topBar = {TopAppBar(title = { Text("Home screen") }) }){
//            padding ->
//        Column(modifier.fillMaxSize().padding(padding).padding(16.dp)){
//            Button(onClick = {
//
//                marimbaSong.start()
//                //navController.navigate("second")
//
//            }) {
//                Text("Click Me")
//            }
//
//            Image(painter = painterResource(R.drawable.github),
//
//                contentDescription = "image",
//                modifier.size(100.dp).
//                clickable {
//                    merengueSong.start()
//                    //navController.navigate("Home")
//                })
//        }
//    }
}
//
//@Composable
//fun ItemScrren(navController : NavController, modifier: Modifier = Modifier){
//    val context = LocalContext.current
//    val merengueSong = MediaPlayer.create(context, R.raw.merengue)
//    Scaffold(topBar = {TopAppBar(title = { Text("Second screen") }) }){
//            padding ->
//        Column(modifier.fillMaxSize().padding(padding).padding(16.dp),
//            verticalArrangement = Arrangement.Center
//        ){
//
//            Button(onClick = {
//
//                merengueSong.start()
//                navController.navigate("Home")})
//            {
//                Text("Click Me")
//            }
//
//            Image(painter = painterResource(R.drawable.github),
//                contentDescription = "image",
//                modifier.size(100.dp).
//                clickable { navController.navigate("Home") })
//        }
//    }
//}