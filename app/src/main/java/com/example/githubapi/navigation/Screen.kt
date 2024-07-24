package com.example.githubapi.navigation

sealed class Screen(val route: String) {
    companion object {
        const val DETAILS_SCREEN_ARGS = "USER_ID"
        private const val USERS_SCREEN = "USERS_SCREEN"
        private const val DETAILS_SCREEN = "DETAILS_SCREEN"
    }

    data object ListScreen : Screen(USERS_SCREEN)

    data object DetailScreen : Screen("$DETAILS_SCREEN/{$DETAILS_SCREEN_ARGS}") {
        fun createRoute(userId: String) = "$DETAILS_SCREEN/$userId"
    }
}
