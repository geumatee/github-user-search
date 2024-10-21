package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geumatee.githubusersearch.R

@Composable
internal fun RepositoryItem(
    name: String,
    language: String?,
    description: String?,
    stargazersCount: String,
    htmlUrl: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    ListItem(
        headlineContent = {
            Row {
                Text(
                    name,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                if (language != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        language,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
        supportingContent = {
            Text(
                description ?: "",
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(
                        R.string.star_image_description
                    )
                )
                Text(
                    stargazersCount,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        modifier = modifier.clickable {
            onClick(htmlUrl)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun RepositoryItemPreview() {
    RepositoryItem(
        name = "Googledasdajsdhajkshdkjashdkjashdkajshdkjashdkjashdkjashdkjashdkajsh",
        language = "Java",
        description = "Google's search engine",
        stargazersCount = "46,292",
        htmlUrl = "https://github.com/google",
        onClick = {}
    )
}