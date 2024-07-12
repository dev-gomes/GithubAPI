package com.example.lib_domain.usecases

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User
import com.example.lib_domain.repository.UserListRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UserListRepository) {

    suspend fun getUserList(): ResultType<List<User>> = repository.getUsers()
}