package com.example.lib_domain.usecases

import com.example.lib_domain.ResultType
import com.example.lib_domain.model.Details
import com.example.lib_domain.repository.DetailsRepository
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
        val detailsList = ResultType.Success(emptyList<Details>())
        coEvery { mockRepository.getUserDetails(userId) } returns detailsList

        val result = subject.getDetails(userId)

        assertEquals(detailsList, result)
    }
}