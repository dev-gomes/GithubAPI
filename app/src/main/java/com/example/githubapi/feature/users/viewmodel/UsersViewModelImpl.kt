package com.example.githubapi.feature.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.feature.users.domain.GetUserListUseCase
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UiState
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserEvent
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserIntent
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserIntent.NavigateToDetail
import com.example.githubapi.navigation.Screen.DetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModelImpl @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : UsersViewModel, ViewModel() {
    private val _userIntent = Channel<UserIntent>()
    override val userIntent = _userIntent.receiveAsFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { fetchUsers() }
    }

    override fun reduce(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.OnItemClicked -> {
                _userIntent.trySend(NavigateToDetail(DetailScreen.createRoute(userEvent.userId)))
            }
        }
    }

    private suspend fun fetchUsers() {
        try {
            val users = getUserListUseCase.getUserList()
            _uiState.value = UiState.Success(users)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown Error")
        }
    }
}

