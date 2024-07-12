package com.example.lib_data.mapper

import com.example.lib_data.model.UserResponse
import com.example.lib_domain.model.User
import javax.inject.Inject

class UserListMapper @Inject constructor() {

    fun from(response: List<UserResponse>): List<User> = response.map {
        User(
            name = it.login,
            avatarUrl = it.avatarUrl
        )
    }
}