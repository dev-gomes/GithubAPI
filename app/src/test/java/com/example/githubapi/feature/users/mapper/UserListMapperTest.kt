package com.example.githubapi.feature.users.mapper

import com.example.githubapi.models.User
import com.example.githubapi.network.model.UserResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class UserListMapperTest {

    private val subject = UserListMapper()

    @Test
    fun `from - maps the response correctly`() {
        val name = "name"
        val avatarUrl = "avatarUrl"
        val userResponse = listOf(UserResponse(name, avatarUrl, 1))
        val expected = User(name = name, avatarUrl = avatarUrl)

        val result = subject.from(userResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }
}