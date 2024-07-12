package com.example.lib_domain.usecases

import com.example.lib_domain.repository.DetailsRepository
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository
) {

    suspend fun getDetails(userId: String) = detailsRepository.getUserDetails(userId)
}