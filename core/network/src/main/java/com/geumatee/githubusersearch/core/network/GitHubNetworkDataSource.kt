package com.geumatee.githubusersearch.core.network

import com.geumatee.githubusersearch.core.model.Repository
import com.geumatee.githubusersearch.core.model.UserDetail
import com.geumatee.githubusersearch.core.model.UserSearchResponse


interface GitHubNetworkDataSource {
    suspend fun searchUsers(query: String, perPage: Int = 100, page: Int = 1): UserSearchResponse

    suspend fun getUserDetail(login: String): UserDetail

    suspend fun getRepositories(login: String, perPage: Int = 100, page: Int = 1): List<Repository>
}