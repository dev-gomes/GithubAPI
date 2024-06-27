package com.example.githubapi.feature.users.mapper

import com.example.githubapi.models.User
import com.example.githubapi.network.model.UserResponse
import javax.inject.Inject

class UserListMapper @Inject constructor() {

    fun from(response: List<UserResponse>): List<User> = response.map {
        User(
            name = it.login,
            avatarUrl = it.avatar_url
        )
    }
}