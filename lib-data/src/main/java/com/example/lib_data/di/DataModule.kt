package com.example.lib_data.di

import com.example.lib_data.repository.DetailsRepositoryImpl
import com.example.lib_data.repository.UserListRepositoryImpl
import com.example.lib_domain.repository.DetailsRepository
import com.example.lib_domain.repository.UserListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindUserListRepository(userListRepositoryImpl: UserListRepositoryImpl): UserListRepository

    @Binds
    abstract fun bindDetailsRepository(detailsRepository: DetailsRepositoryImpl): DetailsRepository
}
