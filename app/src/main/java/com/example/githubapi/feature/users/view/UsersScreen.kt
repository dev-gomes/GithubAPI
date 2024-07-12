package com.example.githubapi.feature.users.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.githubapi.composables.ErrorScreen
import com.example.githubapi.composables.LoadingScreen
import com.example.githubapi.feature.users.viewmodel.UsersViewModel
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UiState
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserEvent
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserIntent
import com.example.githubapi.feature.users.viewmodel.UsersViewModelImpl
import com.example.githubapi.ui.theme.Dimensions
import com.example.githubapi.ui.theme.GithubAPITheme
import com.example.lib_domain.model.User

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel<UsersViewModelImpl>(),
    navigationBlock: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.userIntent.collect { userIntent ->
            when (userIntent) {
                is UserIntent.NavigateToDetail -> {
                    navigationBlock(userIntent.route)
                }
            }
        }
    }

    val state by viewModel.uiState.collectAsState()

    UsersContent(
        state = state,
        onReduce = viewModel::reduce,
    )
}

@Composable
private fun UsersContent(
    state: UiState,
    onReduce: (UserEvent) -> Unit
) {
    when (state) {
        is UiState.Loading -> LoadingScreen()

        is UiState.Success -> UserList(users = state.data) {
            onReduce(UserEvent.OnUserClicked(it))
        }

        is UiState.Error -> ErrorScreen()
    }
}


@Composable
fun UserList(users: List<User>, onItemClick: (String) -> Unit) {
    LazyColumn {
        items(users) {
            UserItem(user = it, onClick = {
                onItemClick(it.name)
            })
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(Dimensions.mediumPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(Dimensions.iconSizeLarge)
        )
        Text(
            modifier = Modifier.padding(start = Dimensions.mediumPadding),
            text = user.name
        )
    }
}

class ScreenStateProvider : PreviewParameterProvider<UiState> {
    override val values = sequenceOf(
        UiState.Success(
            listOf(
                User("Username 1", ""),
                User("Username 2", ""),
                User("Username 3", "")
            )
        ),
        UiState.Error,
        UiState.Loading
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UsersScreenPreview(@PreviewParameter(ScreenStateProvider::class) uiState: UiState) {
    GithubAPITheme {
        UsersContent(
            state = uiState,
            onReduce = { }
        )
    }
}
