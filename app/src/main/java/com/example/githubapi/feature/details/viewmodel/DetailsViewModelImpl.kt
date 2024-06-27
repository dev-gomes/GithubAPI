package com.example.githubapi.feature.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.feature.details.domain.GetDetailsUseCase
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailUiState
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailsEvent
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailsEvent.OnDetailsIdReceived
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase
) : DetailsViewModel, ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    override val uiState: StateFlow<DetailUiState> = _uiState

    override fun reduce(detailsEvent: DetailsEvent) {
        when (detailsEvent) {
            is OnDetailsIdReceived -> loadUserDetails(detailsEvent.userId)
        }
    }

    private fun loadUserDetails(userId: String) {
        viewModelScope.launch {
            try {
                val details = getDetailsUseCase.getDetails(userId)
                _uiState.value = DetailUiState.Success(details)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}