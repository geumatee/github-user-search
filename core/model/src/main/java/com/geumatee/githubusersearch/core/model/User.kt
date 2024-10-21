package com.geumatee.githubusersearch.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
)
