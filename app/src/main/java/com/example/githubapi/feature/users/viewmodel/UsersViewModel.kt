package com.example.githubapi.feature.users.viewmodel

import com.example.githubapi.feature.users.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UsersViewModel {
    val uiState: StateFlow<UiState>
    val userIntent: Flow<UserIntent>

    fun reduce(userEvent: UserEvent)

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<User>) : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class UserIntent {
        data class NavigateToDetail(val route: String) : UserIntent()
    }

    sealed class UserEvent {
        data class OnItemClicked(val userId: String) : UserEvent()
    }
}