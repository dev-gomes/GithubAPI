package com.example.lib_data.repository

import com.example.lib_data.mapper.UserListMapper
import com.example.lib_data.model.UserResponse
import com.example.lib_data.services.GitHubApiService
import com.example.lib_domain.ResultType
import com.example.lib_domain.model.User
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserListRepositoryImplTest {

    private val mockService = mockk<GitHubApiService>()
    private val mockMapper = mockk<UserListMapper>()
    private val subject = UserListRepositoryImpl(mockService, mockMapper)

    @Test
    fun `GIVEN successful API call WHEN getUsers is called THEN user list is returned`() = runTest {
        val mockUserResponse = mockk<UserResponse>()
        val mockUser = mockk<User>()
        coEvery { mockService.getUsers() } returns listOf(mockUserResponse)
        every { mockMapper.from(listOf(mockUserResponse)) } returns listOf(mockUser)

        val result = subject.getUsers()

        assertEquals(ResultType.Success(listOf(mockUser)), result)
        coVerify {
            mockService.getUsers()
            mockMapper.from(listOf(mockUserResponse))
        }
    }

    @Test
    fun `GIVEN un-successful API call WHEN getUsers is called THEN error returned`() = runTest {
        val exception = Exception()
        coEvery { mockService.getUsers() } throws exception

        val result = subject.getUsers()

        assertEquals(ResultType.Error(exception), result)
        coVerify {
            mockService.getUsers()
            mockMapper wasNot Called
        }
    }
}