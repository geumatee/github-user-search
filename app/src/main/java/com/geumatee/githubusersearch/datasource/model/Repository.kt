package com.geumatee.githubusersearch.datasource.model

import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Int,
    val fork: Boolean,
    val name: String,
    val description: String?,
    val language: String?,
    val stargazersCount: Int,
    val htmlUrl: String,
)
