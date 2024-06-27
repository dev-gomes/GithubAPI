package com.example.githubapi.feature.details.domain

import com.example.githubapi.feature.details.repository.DetailsRepository
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository
) {

    suspend fun getDetails(userId: String) = detailsRepository.getUserDetails(userId)
}