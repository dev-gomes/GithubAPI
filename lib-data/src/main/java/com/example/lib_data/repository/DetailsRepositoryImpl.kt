package com.example.lib_data.repository

import com.example.lib_data.mapper.DetailsMapper
import com.example.lib_data.services.GitHubApiService
import com.example.lib_domain.ResultType
import com.example.lib_domain.asResult
import com.example.lib_domain.model.Details
import com.example.lib_domain.repository.DetailsRepository
import com.example.lib_domain.safeApiCall
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val mapper: DetailsMapper
) : DetailsRepository {

    override suspend fun getUserDetails(userId: String): ResultType<List<Details>> =
        safeApiCall { apiService.getDetails(userId) }
            .asResult { mapper.from(it) }
}
