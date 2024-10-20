package com.geumatee.githubusersearch.ui.compose.userdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.geumatee.githubusersearch.datasource.model.Repository
import com.geumatee.githubusersearch.datasource.model.UserDetail
import com.geumatee.githubusersearch.ui.compose.userdetail.compose.BackIcon
import com.geumatee.githubusersearch.ui.compose.userdetail.compose.UserDetailView
import com.geumatee.githubusersearch.ui.compose.userdetail.compose.UserHeader
import com.geumatee.githubusersearch.ui.compose.userdetail.compose.repositoryList
import com.geumatee.githubusersearch.ui.compose.userdetail.model.UserDetailState
import kotlinx.coroutines.flow.flowOf
import java.text.DecimalFormat

@Composable
internal fun UserDetailRoute(
    login: String,
    userDetailViewModel: UserDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    onClick: (String) -> Unit = {},
) {
    val userDetailState = userDetailViewModel.userDetailState.collectAsStateWithLifecycle().value
    val repositories = userDetailViewModel.repositoryPager.collectAsLazyPagingItems()

    userDetailViewModel.setLogin(login)

    UserDetailScreen(
        login = login,
        userDetailState = userDetailState,
        repositories = repositories,
        modifier = modifier,
        navigateBack = navigateBack,
        onClick = onClick,
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun UserDetailScreen(
    login: String,
    userDetailState: UserDetailState,
    repositories: LazyPagingItems<Repository>,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    onClick: (String) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val pullToRefreshState = rememberPullToRefreshState()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                navigationIcon = {
                    BackIcon(navigateBack)
                },
                title = {
                    UserHeader(login, userDetailState.userDetail?.avatarUrl, scrollBehavior)
                },
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = repositories.loadState.refresh is LoadState.Loading,
            onRefresh = { repositories.refresh() },
            state = pullToRefreshState,
            indicator = {
                Indicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = TopAppBarDefaults.LargeAppBarExpandedHeight + TopAppBarDefaults.LargeAppBarCollapsedHeight),
                    isRefreshing = repositories.loadState.refresh is LoadState.Loading,
                    state = pullToRefreshState
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .testTag("repositoryList")
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                stickyHeader {
                    UserDetailView(
                        isLoading = userDetailState.isLoading,
                        error = userDetailState.error,
                        name = userDetailState.userDetail?.name,
                        followers = userDetailState.userDetail?.followers.formatCommaSeparate(),
                        following = userDetailState.userDetail?.following.formatCommaSeparate(),
                    )
                }
                repositoryList(repositories, onClick)
            }
        }

    }
}

@Preview
@Composable
private fun UserDetailScreenPreview() {
    UserDetailScreen(
        login = "google",
        userDetailState = UserDetailState(
            isLoading = false,
            userDetail = UserDetail(
                login = "google",
                id = 1,
                avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                name = "Google",
                followers = 46292,
                following = 0
            )
        ),
        repositories = flowOf(
            PagingData.from(
                listOf(
                    Repository(
                        id = 20300177,
                        fork = false,
                        name = "guava",
                        description = "Google core libraries for Java",
                        htmlUrl = "https://github.com/google/guava",
                        language = "Java",
                        stargazersCount = 50126,
                    ),
                    Repository(
                        id = 35969061,
                        fork = false,
                        name = "styleguide",
                        description = "Style guides for Google-originated open-source projects",
                        htmlUrl = "https://github.com/google/styleguide",
                        language = "HTML",
                        stargazersCount = 37370,
                    ),
                    Repository(
                        id = 297744725,
                        fork = false,
                        name = "ksp",
                        description = "Kotlin Symbol Processing API",
                        htmlUrl = "https://github.com/google/ksp",
                        language = "Kotlin",
                        stargazersCount = 2854,
                    ),
                )
            )
        ).collectAsLazyPagingItems(),
    )
}

// Extension function to format followers count
internal fun Int?.formatCommaSeparate(): String {
    return DecimalFormat("#,###").format(this ?: 0)
}