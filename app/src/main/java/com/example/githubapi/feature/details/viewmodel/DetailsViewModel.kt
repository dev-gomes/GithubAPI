package com.example.githubapi.feature.details.viewmodel

import com.example.lib_domain.model.Details
import kotlinx.coroutines.flow.StateFlow

interface DetailsViewModel {
    val uiState: StateFlow<DetailUiState>

    fun reduce(detailsEvent: DetailsEvent)

    sealed class DetailUiState {
        data object Loading : DetailUiState()
        data class Success(val details: List<Details>) : DetailUiState()
        data object Error : DetailUiState()
    }

    sealed class DetailsEvent {
        data class OnDetailsIdReceived(val userId: String) : DetailsEvent()
    }
}