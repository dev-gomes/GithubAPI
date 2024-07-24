package com.example.githubapi.feature.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.feature.details.viewmodel.DetailsEvent.OnDetailsIdReceived
import com.example.lib_domain.ResultType
import com.example.lib_domain.usecases.GetDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    fun reduce(detailsEvent: DetailsEvent) {
        when (detailsEvent) {
            is OnDetailsIdReceived -> loadUserDetails(detailsEvent.userId)
        }
    }

    private fun loadUserDetails(userId: String) {
        viewModelScope.launch {
            val details = getDetailsUseCase.getDetails(userId)
            _uiState.value = when (details) {
                is ResultType.Success -> DetailUiState.Success(details.data)
                is ResultType.Error -> DetailUiState.Error
            }
        }
    }
}