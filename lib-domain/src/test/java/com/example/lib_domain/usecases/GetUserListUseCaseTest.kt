package com.example.lib_domain.usecases

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User
import com.example.lib_domain.repository.UserListRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetUserListUseCaseTest {

    private val mockRepository = mockk<UserListRepository>()
    private val subject = GetUserListUseCase(mockRepository)

    @Test
    fun `GIVEN Success result type WHEN getUserList is called THEN user list is returned`() =
        runTest {
            val userListFlow = flowOf(ResultType.Success(emptyList<User>()))
            coEvery { mockRepository.getUsers() } returns userListFlow

            val result = subject.getUserList()

            assertEquals(userListFlow, result)
        }
}