package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.geumatee.githubusersearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHeader(
    login: String,
    avatarUrl: String?,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val avatarSize = remember {
        derivedStateOf {
            (72 - (scrollBehavior.state.collapsedFraction * 24)).dp
        }
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = login)
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(avatarSize.value)
                .clip(CircleShape)
        ) {
            SubcomposeAsyncImage(
                model = avatarUrl,
                contentDescription = stringResource(
                    R.string.user_avatar_content_description,
                    login
                ),
                loading = {
                    CircularProgressIndicator()
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun UserHeaderPreview() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    UserHeader(login = "geumatee", avatarUrl = null, scrollBehavior = scrollBehavior)
}