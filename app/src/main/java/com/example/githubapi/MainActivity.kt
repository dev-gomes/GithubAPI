package com.example.githubapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubapi.feature.details.view.DetailsScreen
import com.example.githubapi.feature.users.view.UsersScreen
import com.example.githubapi.ui.navigation.DETAILS_SCREEN_ARGS
import com.example.githubapi.ui.navigation.Screen
import com.example.githubapi.ui.theme.GithubAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ListScreen.route,
        modifier = modifier
    ) {
        composable(Screen.ListScreen.route) {
            UsersScreen(
                navigationBlock = { navController.navigate(it) })
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(DETAILS_SCREEN_ARGS) { type = NavType.StringType })
        ) { backStackEntry ->
            val userId =
                backStackEntry.arguments?.getString(DETAILS_SCREEN_ARGS) ?: return@composable
            DetailsScreen(userId = userId)
        }
    }
}