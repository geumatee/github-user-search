package com.geumatee.githubusersearch.ui.compose.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.geumatee.githubusersearch.core.model.Repository
import com.geumatee.githubusersearch.core.data.RepositoryPagingSource
import com.geumatee.githubusersearch.core.data.RepositoryRepository
import com.geumatee.githubusersearch.core.data.UserRepository
import com.geumatee.githubusersearch.ui.compose.userdetail.model.UserDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val repositoryRepository: RepositoryRepository
) : ViewModel() {
    var login = MutableStateFlow("")
        private set
    val userDetailState: StateFlow<UserDetailState> = login.map {
        try {
            val userDetail = userRepository.getUserDetail(it)
            UserDetailState(
                isLoading = false,
                userDetail = userDetail
            )
        } catch(e: Exception) {
            UserDetailState(
                isLoading = false,
                userDetail = null,
                error = e.message
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserDetailState()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val repositoryPager: Flow<PagingData<Repository>> = login.flatMapLatest { login ->
        Pager(PagingConfig(pageSize = ITEM_PER_PAGE)) {
            RepositoryPagingSource(
                repositoryRepository,
                login,
                ITEM_PER_PAGE
            )
        }.flow
    }.cachedIn(viewModelScope)

    fun setLogin(login: String) {
        this.login.value = login
    }
}

private const val ITEM_PER_PAGE = 20