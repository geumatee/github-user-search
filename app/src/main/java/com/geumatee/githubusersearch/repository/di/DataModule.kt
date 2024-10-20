package com.geumatee.githubusersearch.repository.di

import com.geumatee.githubusersearch.repository.NetworkRepositoryRepository
import com.geumatee.githubusersearch.repository.NetworkUserRepository
import com.geumatee.githubusersearch.repository.RepositoryRepository
import com.geumatee.githubusersearch.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsUserRepository(
        userRepository: NetworkUserRepository,
    ): UserRepository

    @Binds
    internal abstract fun bindsRepositoryRepository(
        repositoryRepository: NetworkRepositoryRepository,
    ): RepositoryRepository

}