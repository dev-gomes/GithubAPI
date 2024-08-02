package com.example.lib_data.repository

import com.example.lib_data.mapper.DetailsMapper
import com.example.lib_data.model.DetailsResponse
import com.example.lib_data.services.GitHubApiService
import com.example.lib_domain.ResultType
import com.example.lib_domain.model.Details
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class DetailsRepositoryImplTest {

    companion object {
        private const val USER_ID = "userId"
    }

    private val mockService = mockk<GitHubApiService>()
    private val mockMapper = mockk<DetailsMapper>()
    private val subject = DetailsRepositoryImpl(mockService, mockMapper)

    @Test
    fun `GIVEN successful API call WHEN getUserDetails is called THEN details list is returned`() =
        runTest {
            val mockDetailsResponse = mockk<DetailsResponse>()
            val mockDetails = mockk<Details>()
            coEvery { mockService.getDetails(USER_ID) } returns Response.success(
                listOf(mockDetailsResponse)
            )
            every { mockMapper.from(listOf(mockDetailsResponse)) } returns listOf(mockDetails)

            val result = subject.getUserDetails(USER_ID)

            assertEquals(ResultType.Success(listOf(mockDetails)), result)
            coVerify {
                mockService.getDetails(USER_ID)
                mockMapper.from(listOf(mockDetailsResponse))
            }
        }

    @Test
    fun `GIVEN un-successful API call WHEN getUserDetails is called THEN error is returned`() =
        runTest {
            val exception = Exception()
            coEvery { mockService.getDetails(USER_ID) } throws exception

            val result = subject.getUserDetails(USER_ID)

            assertEquals(ResultType.Error(exception), result)
            coVerify {
                mockService.getDetails(USER_ID)
                mockMapper wasNot Called
            }
        }
}