package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.ui.navigation.Route
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.Home) {
                    composable<Route.Home> {
                        HomeScreen(
                            onSearchClicked = {
                                navController.navigate(Route.Search)
                            }
                        )
                    }
                    composable<Route.Search> {
                        SearchScreen(
                            onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}