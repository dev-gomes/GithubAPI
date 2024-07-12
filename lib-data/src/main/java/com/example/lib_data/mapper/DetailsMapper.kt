package com.example.lib_data.mapper

import com.example.lib_data.model.DetailsResponse
import com.example.lib_domain.model.Details
import javax.inject.Inject

class DetailsMapper @Inject constructor() {

    fun from(response: List<DetailsResponse>): List<Details> = response.map {
        Details(
            name = it.name,
            description = it.description.orEmpty()
        )
    }
}