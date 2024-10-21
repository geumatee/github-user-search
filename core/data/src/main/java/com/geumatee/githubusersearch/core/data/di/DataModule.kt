package com.geumatee.githubusersearch.core.data.di

import com.geumatee.githubusersearch.core.data.NetworkRepositoryRepository
import com.geumatee.githubusersearch.core.data.NetworkUserRepository
import com.geumatee.githubusersearch.core.data.RepositoryRepository
import com.geumatee.githubusersearch.core.data.UserRepository
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