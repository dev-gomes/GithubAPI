package com.example.githubapi.feature.users.domain

import com.example.githubapi.feature.users.repository.UserListRepository
import com.example.githubapi.feature.users.models.User
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UserListRepository) {

    suspend fun getUserList(): List<User> = repository.getUsers()
}