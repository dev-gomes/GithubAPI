package com.example.lib_data.mapper

import com.example.lib_data.model.DetailsResponse
import com.example.lib_domain.model.Details
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsMapperTest {
    companion object {
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
    }

    private val subject = DetailsMapper()

    @Test
    fun `GIVEN non-null description WHEN from is called THEN response is correctly mapped`() {
        val detailsResponse = listOf(
            DetailsResponse(
                name = NAME,
                description = DESCRIPTION
            )
        )
        val expected = Details(name = NAME, description = DESCRIPTION)

        val result = subject.from(detailsResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }

    @Test
    fun `GIVEN null description WHEN from is called THEN response is correctly mapped`() {
        val detailsResponse = listOf(
            DetailsResponse(
                name = NAME,
                description = null
            )
        )
        val expected = Details(name = NAME, description = "")

        val result = subject.from(detailsResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }
}