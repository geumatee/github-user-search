package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geumatee.githubusersearch.R

@Composable
internal fun UserDetailView(
    isLoading: Boolean,
    error: String?,
    name: String?,
    followers: Int?,
    following: Int?,
    modifier: Modifier = Modifier
) {
    when (isLoading) {
        true -> Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(all = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        false -> when (error) {
            null ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    Text(
                        name ?: "",
                        modifier = Modifier.weight(1f)
                    )
                    StatCounter(stringResource(R.string.followers), followers)
                    Spacer(modifier = Modifier.width(8.dp))
                    StatCounter(stringResource(R.string.following), following)
                }

            else -> Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            }

        }
    }
}

@Preview()
@Composable
private fun UserDetailViewPreview() {
    UserDetailView(
        isLoading = false,
        error = null,
        name = "Google",
        followers = 46292,
        following = 0
    )
}
