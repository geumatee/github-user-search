package com.geumatee.githubusersearch.ui.compose.userdetail.model

import com.geumatee.githubusersearch.datasource.model.UserDetail

data class UserDetailState(
    val isLoading: Boolean = true,
    val userDetail: UserDetail? = null,
    val error: String? = null
)
