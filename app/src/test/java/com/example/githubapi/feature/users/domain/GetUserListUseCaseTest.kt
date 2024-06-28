package com.example.githubapi.feature.users.domain

import com.example.githubapi.feature.users.repository.UserListRepository
import com.example.githubapi.feature.users.models.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetUserListUseCaseTest {

    private val mockRepository = mockk<UserListRepository>()
    private val subject = GetUserListUseCase(mockRepository)

    @Test
    fun `WHEN getUserList is called THEN user list is returned`() = runTest {
        val mockUserList = listOf(mockk<User>())
        coEvery { mockRepository.getUsers() } returns mockUserList

        val result = subject.getUserList()

        assertEquals(mockUserList, result)
    }
}