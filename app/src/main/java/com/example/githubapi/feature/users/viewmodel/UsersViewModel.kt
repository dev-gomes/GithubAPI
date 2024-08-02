package com.example.githubapi.feature.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.feature.users.viewmodel.UserIntent.NavigateToDetail
import com.example.githubapi.navigation.Screen.DetailScreen
import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User
import com.example.lib_domain.usecases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    private val _userIntent = Channel<UserIntent>()
    val userIntent = _userIntent.receiveAsFlow()

    val uiState: StateFlow<UsersUiState> by lazy {
        getUserListUseCase.getUserList()
            .map { mapResult(it) }
            .catch { emit(UsersUiState.Error) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UsersUiState.Loading
            )
    }

    private fun mapResult(result: ResultType<List<User>>) =
        when (result) {
            is ResultType.Success -> UsersUiState.Success(result.data)
            is ResultType.Error -> UsersUiState.Error
        }

    fun reduce(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.OnUserClicked -> {
                _userIntent.trySend(NavigateToDetail(DetailScreen.createRoute(userEvent.userId)))
            }
        }
    }
}