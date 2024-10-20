package com.geumatee.githubusersearch.repository

import com.geumatee.githubusersearch.datasource.GitHubNetworkDataSource
import com.geumatee.githubusersearch.datasource.model.UserDetail
import com.geumatee.githubusersearch.datasource.model.UserSearchResponse
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(
    private val network: GitHubNetworkDataSource
) : UserRepository {
    override suspend fun searchUsers(query: String, perPage: Int, page: Int): UserSearchResponse =
        network.searchUsers(query = query, perPage = perPage, page = page)

    override suspend fun getUserDetail(login: String): UserDetail =
        network.getUserDetail(login)
}