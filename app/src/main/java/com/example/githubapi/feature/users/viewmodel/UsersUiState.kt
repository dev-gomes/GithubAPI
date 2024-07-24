package com.example.githubapi.feature.users.viewmodel

import com.example.lib_domain.model.User

sealed class UsersUiState {
    data object Loading : UsersUiState()
    data class Success(val data: List<User>) : UsersUiState()
    data object Error : UsersUiState()
}

sealed class UserIntent {
    data class NavigateToDetail(val route: String) : UserIntent()
}

sealed class UserEvent {
    data class OnUserClicked(val userId: String) : UserEvent()
}