package com.geumatee.githubusersearch.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geumatee.githubusersearch.core.model.Repository
import java.io.IOException
import javax.inject.Inject

class RepositoryPagingSource @Inject constructor(
    private val repositoryRepository: RepositoryRepository,
    private val login: String,
    private val pageSize: Int,
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = repositoryRepository.getRepositories(
                login = login,
                perPage = pageSize,
                page = nextPageNumber
            )
            LoadResult.Page(
                data = response.filter { repository -> !repository.fork },
                prevKey = null,
                nextKey = if (response.isNotEmpty()) nextPageNumber + 1 else null
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}