package com.example.githubapi.feature.details.mapper

import com.example.githubapi.feature.details.models.Details
import com.example.githubapi.network.model.DetailsResponse
import javax.inject.Inject

class DetailsMapper @Inject constructor() {

    fun from(response: List<DetailsResponse>): List<Details> = response.map {
        Details(
            name = it.name,
            description = it.description ?: ""
        )
    }
}