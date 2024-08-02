package com.example.githubapi.navigation

import com.example.githubapi.util.NavigationConstants.DETAILS_SCREEN
import com.example.githubapi.util.NavigationConstants.DETAILS_SCREEN_ARGS
import com.example.githubapi.util.NavigationConstants.USERS_SCREEN

sealed class Screen(val route: String) {

    data object ListScreen : Screen(USERS_SCREEN)

    data object DetailScreen : Screen("$DETAILS_SCREEN/{$DETAILS_SCREEN_ARGS}") {
        fun createRoute(userId: String) = "$DETAILS_SCREEN/$userId"
    }
}
