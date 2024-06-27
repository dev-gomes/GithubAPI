package com.example.githubapi.network.api

import com.example.githubapi.network.model.DetailsResponse
import com.example.githubapi.network.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users?since=2024")
    suspend fun getUsers(): List<UserResponse>

    @GET("users/{userId}/repos")
    suspend fun getDetails(@Path("userId") userId: String): List<DetailsResponse>
}
