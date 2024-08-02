package com.example.lib_domain.repository

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserListRepository {
    fun getUsers(): Flow<ResultType<List<User>>>
}