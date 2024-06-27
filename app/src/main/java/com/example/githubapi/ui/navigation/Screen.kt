package com.example.githubapi.ui.navigation

private const val USERS_SCREEN = "USERS_SCREEN"
private const val DETAILS_SCREEN = "DETAILS_SCREEN"

const val DETAILS_SCREEN_ARGS = "USER_ID"

sealed class Screen(val route: String) {
    data object ListScreen : Screen(USERS_SCREEN)

    data object DetailScreen : Screen("$DETAILS_SCREEN/{$DETAILS_SCREEN_ARGS}") {
        fun createRoute(userId: String) = "$DETAILS_SCREEN/$userId"
    }
}
