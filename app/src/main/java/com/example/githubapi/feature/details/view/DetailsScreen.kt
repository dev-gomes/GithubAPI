package com.example.githubapi.feature.details.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubapi.composables.ErrorScreen
import com.example.githubapi.composables.LoadingScreen
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailUiState
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailsEvent.OnDetailsIdReceived
import com.example.githubapi.feature.details.viewmodel.DetailsViewModelImpl
import com.example.githubapi.models.Details
import com.example.githubapi.ui.theme.GithubAPITheme

@Composable
fun DetailsScreen(
    userId: String,
    viewModel: DetailsViewModel = hiltViewModel<DetailsViewModelImpl>()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.reduce(OnDetailsIdReceived(userId = userId))
    }

    DetailsContent(state)
}

@Composable
private fun DetailsContent(state: DetailUiState) {
    when (state) {
        is DetailUiState.Loading -> LoadingScreen()
        is DetailUiState.Success -> UserDetails(details = state.details)

        is DetailUiState.Error -> ErrorScreen(errorMessage = state.message)
    }
}

@Composable
fun UserDetails(details: List<Details>) {
    LazyColumn {
        items(details) { DetailsListItem(it) }
    }
}

@Composable
private fun DetailsListItem(it: Details) {
    Text(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
        text = "Repository Name: ${it.name}",
    )
    Text(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        text = "Description: ${it.description}",
        maxLines = 3
    )
    Spacer(modifier = Modifier.height(16.dp))
}

class DetailsScreenStateProvider : PreviewParameterProvider<DetailUiState> {
    override val values = sequenceOf(
        DetailUiState.Success(
            listOf(
                Details("My repository 1", "Description 2"),
                Details("My repository 1", "Description 2")
            )
        ),
        DetailUiState.Error("Error"),
        DetailUiState.Loading
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailsPreview(@PreviewParameter(DetailsScreenStateProvider::class) uiState: DetailUiState) {
    GithubAPITheme {
        DetailsContent(state = uiState)
    }
}