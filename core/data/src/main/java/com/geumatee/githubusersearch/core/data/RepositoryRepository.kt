package com.geumatee.githubusersearch.core.data

import com.geumatee.githubusersearch.core.model.Repository

interface RepositoryRepository {
    suspend fun getRepositories(login: String, perPage: Int, page: Int): List<Repository>
}