package com.example.githubapi.feature.details.di

import com.example.githubapi.feature.details.repository.DetailsRepository
import com.example.githubapi.feature.details.repository.DetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DetailsModule {

    @Binds
    abstract fun bindDetailsRepository(detailsRepository: DetailsRepositoryImpl): DetailsRepository
}
