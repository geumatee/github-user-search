package com.geumatee.githubusersearch.ui.compose.usersearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.geumatee.githubusersearch.R
import com.geumatee.githubusersearch.core.model.User
import com.geumatee.githubusersearch.ui.compose.common.NoResult
import com.geumatee.githubusersearch.ui.compose.common.RefreshError
import com.geumatee.githubusersearch.ui.compose.usersearch.component.SearchTextField
import com.geumatee.githubusersearch.ui.compose.usersearch.component.UserList
import kotlinx.coroutines.flow.flowOf


@Composable
internal fun UserSearchRoute(
    userSearchViewModel: UserSearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClick: (Int, String, String) -> Unit = { _,_, _ -> },
) {
    val users = userSearchViewModel.userPager.collectAsLazyPagingItems()
    val query = userSearchViewModel.query.collectAsState().value
    UserSearchScreen(
        query = query,
        onSearchQueryChanged = userSearchViewModel::setQuery,
        users = users,
        modifier = modifier,
        onClick = onClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserSearchScreen(
    query: String,
    onSearchQueryChanged: (String) -> Unit = {},
    users: LazyPagingItems<User>,
    modifier: Modifier = Modifier,
    onClick: (Int, String, String) -> Unit = { _, _, _ -> },
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchTextField(
                query = query,
                onQueryChanged = onSearchQueryChanged,
            )
            PullToRefreshBox(
                isRefreshing = users.loadState.refresh is LoadState.Loading,
                onRefresh = { users.refresh() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("userList")
            ) {
                when (users.loadState.refresh) {
                    is LoadState.Error -> {
                        RefreshError(
                            (users.loadState.refresh as LoadState.Error).error.message
                                ?: stringResource(R.string.something_went_wrong)
                        )
                    }

                    else -> {
                        when (users.itemCount == 0) {
                            true -> {
                                NoResult()
                            }

                            false -> {
                                UserList(
                                    users = users,
                                    onClick = onClick
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserSearchScreenPreview() {
    UserSearchScreen(
        query = "",
        onSearchQueryChanged = {},
        users = flowOf(
            PagingData.from(
                listOf(
                    User(
                        login = "geumatee",
                        id = 1,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
                    ),
                    User(
                        login = "Google",
                        id = 2,
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
        ).collectAsLazyPagingItems(),
    )
}