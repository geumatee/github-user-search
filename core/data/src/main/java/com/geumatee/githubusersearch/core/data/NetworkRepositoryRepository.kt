package com.geumatee.githubusersearch.core.data

import com.geumatee.githubusersearch.core.network.GitHubNetworkDataSource
import com.geumatee.githubusersearch.core.model.Repository
import javax.inject.Inject

class NetworkRepositoryRepository @Inject constructor(
    private val network: GitHubNetworkDataSource
) : RepositoryRepository {
    override suspend fun getRepositories(login: String, perPage: Int, page: Int): List<Repository> =
        network.getRepositories(login = login, perPage = perPage, page = page)
}