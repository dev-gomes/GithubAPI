package com.example.lib_domain.repository

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.Details

interface DetailsRepository {
    suspend fun getUserDetails(userId: String): ResultType<List<Details>>
}
