package com.example.lib_domain.usecases

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User
import com.example.lib_domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UserListRepository) {

    fun getUserList(): Flow<ResultType<List<User>>> = repository.getUsers()
}