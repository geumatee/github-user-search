package com.geumatee.githubusersearch.ui.navigation.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailRoute(
    val id: Int,
    val login: String,
    val avatarUrl: String,
)
