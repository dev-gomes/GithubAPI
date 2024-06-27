package com.example.githubapi.network.api

import com.example.githubapi.network.model.UserResponse
import retrofit2.http.GET

interface GitHubApiService {
    @GET("users?since=2024")
    suspend fun getUsers(): List<UserResponse>
}
