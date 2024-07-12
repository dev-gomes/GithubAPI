package com.example.lib_domain.model

import com.example.lib_domain.ResultType
import com.example.lib_domain.asResult
import com.example.lib_domain.safeApiCall
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SafeApiCallTest {

    private val mockApiCall = mockk<suspend () -> String>()

    @Test
    fun `GIVEN apiCall succeeds WHEN safeApiCall is called THEN returns Success with data`() =
        runTest {
            val expectedData = "Success Data"
            coEvery { mockApiCall() } returns expectedData

            val result = safeApiCall(mockApiCall)

            assertEquals(ResultType.Success(expectedData), result)
        }

    @Test
    fun `GIVEN apiCall throws an exception WHEN safeApiCall is called THEN returns Error with exception`() =
        runTest {
            val expectedException = Exception()
            coEvery { mockApiCall() } throws expectedException

            val result = safeApiCall(mockApiCall)

            assertEquals(ResultType.Error(expectedException), result)
        }

    @Test
    fun `GIVEN success result WHEN asResults is called THEN maps success`() {
        val result = ResultType.Success("data")
        val mappedResult = result.asResult { it.length }

        assertEquals(ResultType.Success(4), mappedResult)
    }

    @Test
    fun `GIVEN error result WHEN asResults is called THEN maps error`() {
        val exception = Exception()
        val result = ResultType.Error(exception)
        val mappedResult = result.asResult { exception }

        assertEquals(ResultType.Error(result.exception), mappedResult)
    }
}