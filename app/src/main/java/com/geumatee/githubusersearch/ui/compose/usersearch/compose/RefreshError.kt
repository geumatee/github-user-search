package com.geumatee.githubusersearch.ui.compose.usersearch.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.geumatee.githubusersearch.ui.compose.common.RefreshError

@Preview(showBackground = true)
@Composable
internal fun RefreshErrorPreview() {
    RefreshError(message = "Something went wrong")
}