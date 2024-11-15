package com.geumatee.githubusersearch.core.data.di

import com.geumatee.githubusersearch.core.data.MockRepositoryRepository
import com.geumatee.githubusersearch.core.data.MockUserRepository
import com.geumatee.githubusersearch.core.data.NetworkRepositoryRepository
import com.geumatee.githubusersearch.core.data.NetworkUserRepository
import com.geumatee.githubusersearch.core.data.RepositoryRepository
import com.geumatee.githubusersearch.core.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsUserRepository(
        userRepository: NetworkUserRepository,
//        userRepository: MockUserRepository,
    ): UserRepository

    @Binds
    internal abstract fun bindsRepositoryRepository(
        repositoryRepository: NetworkRepositoryRepository,
//        repositoryRepository: MockRepositoryRepository,
    ): RepositoryRepository

}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataModule::class])
interface MockDataModule {

    @Binds
    fun bindsUserRepository(
        userRepository: MockUserRepository,
    ): UserRepository

    @Binds
    fun bindsRepositoryRepository(
        repositoryRepository: MockRepositoryRepository,
    ): RepositoryRepository

}