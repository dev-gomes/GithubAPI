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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubapi.R
import com.example.githubapi.composables.ErrorScreen
import com.example.githubapi.composables.LoadingScreen
import com.example.githubapi.feature.details.viewmodel.DetailUiState
import com.example.githubapi.feature.details.viewmodel.DetailUiState.Error
import com.example.githubapi.feature.details.viewmodel.DetailUiState.Loading
import com.example.githubapi.feature.details.viewmodel.DetailUiState.Success
import com.example.githubapi.feature.details.viewmodel.DetailsEvent.OnDetailsIdReceived
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel
import com.example.githubapi.ui.theme.Dimensions
import com.example.githubapi.ui.theme.GithubAPITheme
import com.example.lib_domain.model.Details

@Composable
fun DetailsScreen(
    userId: String,
    viewModel: DetailsViewModel = hiltViewModel()
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
        is Loading -> LoadingScreen()
        is Success -> UserDetails(details = state.details)
        is Error -> ErrorScreen()
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
        modifier = Modifier.padding(
            start = Dimensions.smallPadding,
            end = Dimensions.smallPadding,
            top = Dimensions.smallPadding
        ),
        text = stringResource(R.string.repository_name, it.name),
    )
    Text(
        modifier = Modifier.padding(
            start = Dimensions.smallPadding,
            end = Dimensions.smallPadding
        ),
        text = stringResource(R.string.description, it.description),
        maxLines = 3
    )
    Spacer(modifier = Modifier.height(Dimensions.spacingMedium))
}

class DetailsScreenStateProvider : PreviewParameterProvider<DetailUiState> {
    override val values = sequenceOf(
        Success(
            listOf(
                Details("My repository 1", "Description 2"),
                Details("My repository 1", "Description 2")
            )
        ),
        Error,
        Loading
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailsPreview(@PreviewParameter(DetailsScreenStateProvider::class) uiState: DetailUiState) {
    GithubAPITheme {
        DetailsContent(state = uiState)
    }
}