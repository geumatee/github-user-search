package com.geumatee.githubusersearch.ui.compose.usersearch.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.geumatee.githubusersearch.R
import com.geumatee.githubusersearch.core.model.User
import com.geumatee.githubusersearch.ui.compose.common.LoadMoreError
import com.geumatee.githubusersearch.ui.compose.common.LoadingView
import com.geumatee.githubusersearch.ui.compose.usersearch.UserItem
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun UserList(
    users: LazyPagingItems<User>,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(
            count = users.itemCount,
            key = { users[it]?.id ?: 0 }
        ) { index ->
            users[index]?.let { UserItem(user = it, onClick = onClick) }
        }
        item {
            when (users.loadState.append) {
                is LoadState.Loading -> LoadingView()
                is LoadState.Error -> LoadMoreError(
                    message = (users.loadState.append as LoadState.Error).error.message
                        ?: stringResource(R.string.something_went_wrong),
                    onRetryClick = { users.retry() })

                is LoadState.NotLoading -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListPreview() {
    UserList(
        users = flowOf(
            PagingData.from(
                listOf(
                    User(
                        login = "geumatee",
                        id = 1,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
                    )
                ),
                sourceLoadStates =
                LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
            )
        ).collectAsLazyPagingItems()
    )
}