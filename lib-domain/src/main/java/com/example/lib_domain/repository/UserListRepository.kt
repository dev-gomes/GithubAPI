package com.example.lib_domain.repository

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User

interface UserListRepository {
    suspend fun getUsers(): ResultType<List<User>>
}