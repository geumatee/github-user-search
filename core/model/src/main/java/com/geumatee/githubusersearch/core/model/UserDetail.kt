package com.geumatee.githubusersearch.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetail(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int,
)
