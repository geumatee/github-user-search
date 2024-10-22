package com.geumatee.githubusersearch.ui.compose.usersearch.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.geumatee.githubusersearch.R
import com.geumatee.githubusersearch.core.model.User

@Composable
fun UserItem(
    user: User,
    onClick: (Int, String, String) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier
) {
    ListItem(
        leadingContent = {
            SubcomposeAsyncImage(
                model = user.avatarUrl,
                contentDescription = stringResource(
                    R.string.user_avatar_content_description,
                    user.login
                ),
                loading = {
                    CircularProgressIndicator()
                },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        },
        headlineContent = {
            Text(
                text = user.login
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(user.id, user.login, user.avatarUrl)
            }
    )
}


@Preview(showBackground = true)
@Composable
private fun UserItemPreview() {
    UserItem(
        user = User(
            login = "geumatee",
            id = 1,
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
        ),
    )
}