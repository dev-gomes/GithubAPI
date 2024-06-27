package com.example.githubapi.ui.navigation

private const val USERS_SCREEN = "USERS_SCREEN"

sealed class Screen(val route: String) {
    data object ListScreen : Screen(USERS_SCREEN)
}
