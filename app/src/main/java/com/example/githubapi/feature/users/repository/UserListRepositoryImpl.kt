package com.example.githubapi.feature.users.repository

import com.example.githubapi.feature.users.mapper.UserListMapper
import com.example.githubapi.models.User
import com.example.githubapi.network.api.GitHubApiService
import javax.inject.Inject

interface UserListRepository {
    suspend fun getUsers(): List<User>
}

class UserListRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val mapper: UserListMapper
) : UserListRepository {

    override suspend fun getUsers() = mapper.from(apiService.getUsers())
}