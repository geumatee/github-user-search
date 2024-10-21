package com.geumatee.githubusersearch.core.data

import com.geumatee.githubusersearch.core.model.UserDetail
import com.geumatee.githubusersearch.core.model.UserSearchResponse

interface UserRepository {
    suspend fun searchUsers(query: String, perPage: Int, page: Int): UserSearchResponse

    suspend fun getUserDetail(login: String): UserDetail
}