package com.geumatee.githubusersearch.core.model

import kotlinx.serialization.Serializable

@Serializable()
data class UserSearchResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<User>,
)
