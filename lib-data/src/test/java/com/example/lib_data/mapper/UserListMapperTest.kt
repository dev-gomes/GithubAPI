package com.example.lib_data.mapper

import com.example.lib_data.model.UserResponse
import com.example.lib_domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserListMapperTest {

    private val subject = UserListMapper()

    @Test
    fun `GIVEN UserResponse WHEN from is called THEN User is returned`() {
        val name = "name"
        val avatarUrl = "avatarUrl"
        val id = 1
        val userResponse = listOf(UserResponse(name, avatarUrl, id))
        val expected = User(name = name, avatarUrl = avatarUrl)

        val result = subject.from(userResponse)

        assertEquals(1, result.size)
        assertEquals(expected, result.first())
    }
}