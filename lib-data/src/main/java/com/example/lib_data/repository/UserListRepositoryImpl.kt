package com.example.lib_data.repository

import com.example.lib_data.mapper.UserListMapper
import com.example.lib_data.services.GitHubApiService
import com.example.lib_domain.ResultType
import com.example.lib_domain.asResult
import com.example.lib_domain.model.User
import com.example.lib_domain.repository.UserListRepository
import com.example.lib_domain.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val mapper: UserListMapper
) : UserListRepository {

    override fun getUsers(): Flow<ResultType<List<User>>> = flow {
        val result = safeApiCall { apiService.getUsers() }
        emit(result.asResult { mapper.from(it) })
    }
}