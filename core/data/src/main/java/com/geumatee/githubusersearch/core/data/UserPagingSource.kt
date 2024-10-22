package com.geumatee.githubusersearch.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geumatee.githubusersearch.core.model.User
import java.io.IOException
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val userRepository: UserRepository,
    private val query: String,
    private val pageSize: Int,
    private var totalCount: Int = 0,
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        if (query.isBlank()) {
            return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
        }
        val nextPageNumber = params.key ?: 1
        return try {
            val response = userRepository.searchUsers(query, pageSize, nextPageNumber)
            totalCount += response.items.size
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = when (response.totalCount > totalCount) {
                    true -> nextPageNumber + 1
                    false -> null
                }
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
