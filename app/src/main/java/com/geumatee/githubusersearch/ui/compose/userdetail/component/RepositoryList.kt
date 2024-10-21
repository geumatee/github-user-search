package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.geumatee.githubusersearch.R
import com.geumatee.githubusersearch.core.model.Repository
import com.geumatee.githubusersearch.ui.compose.userdetail.formatCommaSeparate
import com.geumatee.githubusersearch.ui.compose.common.LoadMoreError
import com.geumatee.githubusersearch.ui.compose.common.LoadingView
import com.geumatee.githubusersearch.ui.compose.common.NoResult
import com.geumatee.githubusersearch.ui.compose.common.RefreshError

internal fun LazyListScope.repositoryList(
    repositories: LazyPagingItems<Repository>,
    onClick: (String) -> Unit
) {
    when (repositories.loadState.refresh) {
        is LoadState.Error -> {
            item {
                RefreshError(
                    (repositories.loadState.refresh as LoadState.Error).error.message
                        ?: stringResource(R.string.something_went_wrong)
                )
            }
        }

        else -> {
            when (repositories.itemCount == 0) {
                true -> {
                    item {
                        NoResult()
                    }
                }

                false -> {
                    items(
                        count = repositories.itemCount,
                        key = { repositories[it]?.id ?: 0 }) { index ->
                        repositories[index]?.let { repository ->
                            RepositoryItem(
                                name = repository.name,
                                language = repository.language,
                                description = repository.description,
                                stargazersCount = repository.stargazersCount.formatCommaSeparate(),
                                htmlUrl = repository.htmlUrl,
                                onClick = onClick
                            )
                            if (index < repositories.itemCount - 1) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                    item {
                        when (repositories.loadState.append) {
                            is LoadState.Loading -> LoadingView()
                            is LoadState.Error -> LoadMoreError(
                                message = (repositories.loadState.append as LoadState.Error).error.message
                                    ?: stringResource(R.string.something_went_wrong),
                                onRetryClick = { repositories.retry() })

                            is LoadState.NotLoading -> Unit
                        }
                    }
                }
            }
        }

    }
}