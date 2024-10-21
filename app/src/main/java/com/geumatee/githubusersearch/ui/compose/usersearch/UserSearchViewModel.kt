package com.geumatee.githubusersearch.ui.compose.usersearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.geumatee.githubusersearch.core.data.UserPagingSource
import com.geumatee.githubusersearch.core.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    var query = MutableStateFlow("")
        private set
    val userPager = query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            Pager(PagingConfig(pageSize = ITEM_PER_PAGE)) {
                UserPagingSource(
                    userRepository,
                    query,
                    ITEM_PER_PAGE
                )
            }.flow
        }
        .cachedIn(viewModelScope)

    fun setQuery(query: String) {
        this.query.value = query
    }
}

private const val ITEM_PER_PAGE = 20