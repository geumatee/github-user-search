package com.geumatee.githubusersearch.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val code: String? = null,
    val message: String? = null
)