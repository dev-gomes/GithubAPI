package com.example.githubapi.feature.users.di

import com.example.githubapi.feature.users.repository.UserListRepository
import com.example.githubapi.feature.users.repository.UserListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UsersModule {

    @Binds
    abstract fun bindUserListRepository(userListRepositoryImpl: UserListRepositoryImpl): UserListRepository
}
