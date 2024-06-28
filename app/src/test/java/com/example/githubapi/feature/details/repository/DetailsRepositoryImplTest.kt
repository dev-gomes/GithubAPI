package com.example.githubapi.feature.details.repository

import com.example.githubapi.feature.details.mapper.DetailsMapper
import com.example.githubapi.feature.details.models.Details
import com.example.githubapi.network.api.GitHubApiService
import com.example.githubapi.network.model.DetailsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsRepositoryImplTest {

    private val mockService = mockk<GitHubApiService>()
    private val mockMapper = mockk<DetailsMapper>()
    private val subject = DetailsRepositoryImpl(mockService, mockMapper)

    @Test
    fun `WHEN getUserDetails is called THEN details list is returned`() = runTest {
        val userId = "userId"
        val mockDetailsResponse = mockk<DetailsResponse>()
        val mockDetails = mockk<Details>()
        coEvery { mockService.getDetails(userId) } returns listOf(mockDetailsResponse)
        every { mockMapper.from(listOf(mockDetailsResponse)) } returns listOf(mockDetails)

        val result = subject.getUserDetails(userId)

        assertEquals(listOf(mockDetails), result)
        coVerify {
            mockService.getDetails(userId)
            mockMapper.from(listOf(mockDetailsResponse))
        }
    }
}