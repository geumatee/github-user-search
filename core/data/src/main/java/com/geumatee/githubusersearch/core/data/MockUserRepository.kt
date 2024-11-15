package com.geumatee.githubusersearch.core.data

import com.geumatee.githubusersearch.core.model.User
import com.geumatee.githubusersearch.core.model.UserDetail
import com.geumatee.githubusersearch.core.model.UserSearchResponse
import javax.inject.Inject

class MockUserRepository @Inject constructor() : UserRepository {
    override suspend fun searchUsers(query: String, perPage: Int, page: Int): UserSearchResponse {
        val users = listOf(
            User(
                login = "google",
                id = 1,
                avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
            ),
            User(
                login = "facebook",
                id = 2,
                avatarUrl = "https://avatars.githubusercontent.com/u/69631?v=4",
            ),
            User(
                login = "microsoft",
                id = 3,
                avatarUrl = "https://avatars.githubusercontent.com/u/6154722?v=4",
            )
        )
        val filteredUsers = users.filter { it.login.contains(query, ignoreCase = true) }
        val userSearchResponse = filteredUsers.subList(
                0,
                perPage.coerceAtMost(filteredUsers.size)
            )
        return UserSearchResponse(
            totalCount = userSearchResponse.size,
            incompleteResults = false,
            items = userSearchResponse
        )
    }

    override suspend fun getUserDetail(login: String): UserDetail {
        val userDetails = listOf(
            UserDetail(
                login = "google",
                id = 1,
                avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                name = "Google",
                followers = 46292,
                following = 0
            ),
            UserDetail(
                login = "facebook",
                id = 2,
                avatarUrl = "https://avatars.githubusercontent.com/u/69631?v=4",
                name = "Facebook",
                followers = 26667,
                following = 0
            ),
            UserDetail(
                login = "microsoft",
                id = 3,
                avatarUrl = "https://avatars.githubusercontent.com/u/6154722?v=4",
                name = "Microsoft",
                followers = 79033,
                following = 0
            )
        )
        return userDetails.first { it.login == login }
    }
}