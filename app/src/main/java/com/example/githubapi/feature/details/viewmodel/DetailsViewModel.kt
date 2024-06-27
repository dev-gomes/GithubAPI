package com.example.githubapi.feature.details.viewmodel

import com.example.githubapi.models.Details
import kotlinx.coroutines.flow.StateFlow

interface DetailsViewModel {
    val uiState: StateFlow<DetailUiState>

    fun reduce(detailsEvent: DetailsEvent)

    sealed class DetailUiState {
        data object Loading : DetailUiState()
        data class Success(val details: List<Details>) : DetailUiState()
        data class Error(val message: String) : DetailUiState()
    }

    sealed class DetailsEvent {
        data class OnDetailsIdReceived(val userId: String) : DetailsEvent()
    }
}