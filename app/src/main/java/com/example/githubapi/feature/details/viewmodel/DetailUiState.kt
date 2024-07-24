package com.example.githubapi.feature.details.viewmodel

import com.example.lib_domain.model.Details

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Success(val details: List<Details>) : DetailUiState()
    data object Error : DetailUiState()
}

sealed class DetailsEvent {
    data class OnDetailsIdReceived(val userId: String) : DetailsEvent()
}