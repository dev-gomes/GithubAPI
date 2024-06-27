package com.example.githubapi.feature.users.repository

import com.example.githubapi.feature.users.mapper.UserListMapper
import com.example.githubapi.models.User
import com.example.githubapi.network.api.GitHubApiService
import com.example.githubapi.network.model.UserResponse
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
    fun `WHEN getUsers is called THEN user list is returned`() = runTest {
        val mockUserResponse = mockk<UserResponse>()
        val mockUser = mockk<User>()
        coEvery { mockService.getUsers() } returns listOf(mockUserResponse)
        every { mockMapper.from(listOf(mockUserResponse)) } returns listOf(mockUser)

        val result = subject.getUsers()

        assertEquals(listOf(mockUser), result)
        coVerify {
            mockService.getUsers()
            mockMapper.from(listOf(mockUserResponse))
        }
    }
}