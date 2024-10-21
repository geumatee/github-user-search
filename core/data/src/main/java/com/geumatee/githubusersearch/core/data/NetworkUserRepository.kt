package com.geumatee.githubusersearch.core.data

import com.geumatee.githubusersearch.core.model.UserDetail
import com.geumatee.githubusersearch.core.network.GitHubNetworkDataSource
import com.geumatee.githubusersearch.core.model.UserSearchResponse
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(
    private val network: GitHubNetworkDataSource
) : UserRepository {
    override suspend fun searchUsers(query: String, perPage: Int, page: Int): UserSearchResponse =
        network.searchUsers(query = query, perPage = perPage, page = page)

    override suspend fun getUserDetail(login: String): UserDetail =
        network.getUserDetail(login)
}