package com.geumatee.githubusersearch.repository

import com.geumatee.githubusersearch.datasource.model.UserDetail
import com.geumatee.githubusersearch.datasource.model.UserSearchResponse

interface UserRepository {
    suspend fun searchUsers(query: String, perPage: Int, page: Int): UserSearchResponse

    suspend fun getUserDetail(login: String): UserDetail
}