package com.geumatee.githubusersearch.repository

import com.geumatee.githubusersearch.datasource.model.Repository

interface RepositoryRepository {
    suspend fun getRepositories(login: String, perPage: Int, page: Int): List<Repository>
}