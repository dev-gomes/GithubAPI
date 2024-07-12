package com.example.lib_data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val id: Int
)