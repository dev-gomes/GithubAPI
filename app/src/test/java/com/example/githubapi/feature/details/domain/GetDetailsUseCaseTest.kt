package com.example.githubapi.feature.details.domain

import com.example.githubapi.feature.details.repository.DetailsRepository
import com.example.githubapi.models.Details
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetDetailsUseCaseTest {

    private val mockRepository = mockk<DetailsRepository>()
    private val subject = GetDetailsUseCase(mockRepository)

    @Test
    fun `WHEN getDetails is called THEN detail list is returned`() = runTest {
        val userId = "userId"
        val mockDetailsList = listOf(mockk<Details>())
        coEvery { mockRepository.getUserDetails(userId) } returns mockDetailsList

        val result = subject.getDetails(userId)

        assertEquals(mockDetailsList, result)
    }
}