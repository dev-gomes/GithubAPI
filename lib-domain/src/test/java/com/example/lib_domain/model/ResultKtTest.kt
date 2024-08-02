package com.example.lib_domain.model

import com.example.lib_domain.ResultType
import com.example.lib_domain.asResult
import com.example.lib_domain.safeApiCall
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class SafeApiCallTest {
    companion object {
        private const val SUCCESS_DATA = "Success Data"
    }

    private val mockApiCall = mockk<suspend () -> Response<String>>()

    @Test
    fun `GIVEN apiCall succeeds WHEN safeApiCall is called THEN Success with data is returned`() =
        runTest {
            coEvery { mockApiCall() } returns Response.success(SUCCESS_DATA)

            val result = safeApiCall(mockApiCall)

            assertEquals(ResultType.Success(SUCCESS_DATA), result)
        }

    @Test
    fun `GIVEN apiCall throws an exception WHEN safeApiCall is called THEN Error with exception is returned`() =
        runTest {
            val expectedException = Exception()
            coEvery { mockApiCall() } throws expectedException

            val result = safeApiCall(mockApiCall)

            assertEquals(ResultType.Error(expectedException), result)
        }

    @Test
    fun `GIVEN success result WHEN asResults is called THEN success is mapped`() {
        val result = ResultType.Success(SUCCESS_DATA)
        val mappedResult = result.asResult { it.length }

        assertEquals(ResultType.Success(12), mappedResult)
    }

    @Test
    fun `GIVEN error result WHEN asResults is called THEN error is mapped`() {
        val exception = Exception()
        val result = ResultType.Error(exception)
        val mappedResult = result.asResult { exception }

        assertEquals(ResultType.Error(result.exception), mappedResult)
    }

    @Test
    fun `GIVEN successful response without body WHEN toResult is called THEN Error is returned`() =
        runTest {
            coEvery { mockApiCall() } returns Response.success(null)

            val result = safeApiCall(mockApiCall)

            assertTrue(result is ResultType.Error)
        }

    @Test
    fun `GIVEN successful response with body WHEN toResult is called THEN Success is returned`() =
        runTest {
            coEvery { mockApiCall() } returns Response.success(SUCCESS_DATA)

            val result = safeApiCall(mockApiCall)

            assertTrue(result is ResultType.Success)
            with(result as ResultType.Success) {
                assertEquals(SUCCESS_DATA, data)
            }
        }

    @Test
    fun `GIVEN failed response WHEN toResult is called THEN Error is returned`() = runTest {
        val mockResponseBody = mockk<ResponseBody>(relaxed = true)
        val response: Response<String> = Response.error(404, mockResponseBody)
        coEvery { mockApiCall() } returns response

        val result = safeApiCall(mockApiCall)

        assertTrue(result is ResultType.Error)
    }
}