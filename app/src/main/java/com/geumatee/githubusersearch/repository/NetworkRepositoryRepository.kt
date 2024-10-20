package com.geumatee.githubusersearch.repository

import com.geumatee.githubusersearch.datasource.GitHubNetworkDataSource
import com.geumatee.githubusersearch.datasource.model.Repository
import javax.inject.Inject

class NetworkRepositoryRepository @Inject constructor(
    private val network: GitHubNetworkDataSource
) : RepositoryRepository {
    override suspend fun getRepositories(login: String, perPage: Int, page: Int): List<Repository> =
        network.getRepositories(login = login, perPage = perPage, page = page)
}