package com.example.githubapi.feature.details.mapper

import com.example.githubapi.models.Details
import com.example.githubapi.network.model.DetailsResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsMapperTest {

    private val subject = DetailsMapper()

    @Test
    fun `GIVEN non-null description WHEN from is called THEN response is correctly mapped`() {
        val name = "name"
        val description = "description"
        val detailsResponse = listOf(DetailsResponse(name = name, description = description))
        val expected = Details(name = name, description = description)

        val result = subject.from(detailsResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }

    @Test
    fun `GIVEN null description WHEN from is called THEN response is correctly mapped`() {
        val name = "name"
        val detailsResponse = listOf(DetailsResponse(name = name, description = null))
        val expected = Details(name = name, description = "")

        val result = subject.from(detailsResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }
}