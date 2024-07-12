package com.example.lib_data.mapper

import com.example.lib_data.model.DetailsResponse
import com.example.lib_domain.model.Details
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsMapperTest {

    private val subject = DetailsMapper()
    private val name = "name"

    @Test
    fun `GIVEN non-null description WHEN from is called THEN response is correctly mapped`() {
        val description = "description"
        val detailsResponse = listOf(
            DetailsResponse(
                name = name,
                description = description
            )
        )
        val expected = Details(name = name, description = description)

        val result = subject.from(detailsResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }

    @Test
    fun `GIVEN null description WHEN from is called THEN response is correctly mapped`() {
        val detailsResponse = listOf(
            DetailsResponse(
                name = name,
                description = null
            )
        )
        val expected = Details(name = name, description = "")

        val result = subject.from(detailsResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }
}