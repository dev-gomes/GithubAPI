package com.example.githubapi.feature.details.repository

import com.example.githubapi.feature.details.mapper.DetailsMapper
import com.example.githubapi.models.Details
import com.example.githubapi.network.api.GitHubApiService
import javax.inject.Inject

interface DetailsRepository {
    suspend fun getUserDetails(userId: String): List<Details>
}

class DetailsRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val mapper: DetailsMapper
) : DetailsRepository {

    override suspend fun getUserDetails(userId: String): List<Details> =
        mapper.from(apiService.getDetails(userId))
}