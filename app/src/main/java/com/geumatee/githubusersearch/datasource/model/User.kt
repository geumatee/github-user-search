package com.geumatee.githubusersearch.datasource.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
)
