package com.example.githubapi.feature.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.feature.users.viewmodel.UserIntent.NavigateToDetail
import com.example.githubapi.navigation.Screen.DetailScreen
import com.example.lib_domain.ResultType
import com.example.lib_domain.usecases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    private val _userIntent = Channel<UserIntent>()
    val userIntent = _userIntent.receiveAsFlow()

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { fetchUsers() }
    }

    fun reduce(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.OnUserClicked -> {
                _userIntent.trySend(NavigateToDetail(DetailScreen.createRoute(userEvent.userId)))
            }
        }
    }

    private suspend fun fetchUsers() {
        _uiState.value = when (val result = getUserListUseCase.getUserList()) {
            is ResultType.Success -> UsersUiState.Success(result.data)
            is ResultType.Error -> UsersUiState.Error
        }
    }
}