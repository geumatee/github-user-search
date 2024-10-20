package com.geumatee.githubusersearch.datasource.retrofit

import com.geumatee.githubusersearch.BuildConfig
import com.geumatee.githubusersearch.datasource.GitHubNetworkDataSource
import com.geumatee.githubusersearch.datasource.model.Repository
import com.geumatee.githubusersearch.datasource.model.ResponseError
import com.geumatee.githubusersearch.datasource.model.UserDetail
import com.geumatee.githubusersearch.datasource.model.UserSearchResponse
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitGitHubNetworkApi {
    @GET(value = "search/users")
    suspend fun searchUsers(
        @HeaderMap map: Map<String, String>,
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ): UserSearchResponse

    @GET(value = "users/{login}")
    suspend fun getUserDetail(
        @HeaderMap map: Map<String, String>,
        @Path("login") login: String,
    ): UserDetail

    @GET(value = "users/{login}/repos")
    suspend fun getRepositories(
        @HeaderMap map: Map<String, String>,
        @Path("login") login: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ): List<Repository>
}

private const val GITHUB_BASE_URL = BuildConfig.BACKEND_URL

private val HEADER_MAP = mapOf(
    "Accept" to "application/vnd.github+json",
    "Authorization" to "Bearer ${BuildConfig.github_rest_api_token}",
    "X-GitHub-Api-Version" to "2022-11-28",
)

@Singleton
internal class RetrofitGitHubNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : GitHubNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(GITHUB_BASE_URL)
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitGitHubNetworkApi::class.java)

    override suspend fun searchUsers(query: String, perPage: Int, page: Int): UserSearchResponse {
        try {
            return networkApi.searchUsers(
                map = HEADER_MAP,
                query = query,
                perPage = perPage,
                page = page,
            )
        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string() ?: ""
            val json = Json { ignoreUnknownKeys = true }
            val responseError = json.decodeFromString(ResponseError.serializer(), body)
            throw Exception(responseError.message, e)
        }
    }


    override suspend fun getUserDetail(login: String): UserDetail {
        try {
            return networkApi.getUserDetail(HEADER_MAP, login)
        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string() ?: ""
            val json = Json { ignoreUnknownKeys = true }
            val responseError = json.decodeFromString(ResponseError.serializer(), body)
            throw Exception(responseError.message, e)
        }
    }

    override suspend fun getRepositories(login: String, perPage: Int, page: Int): List<Repository> {
        try {
            return networkApi.getRepositories(
                HEADER_MAP,
                login,
                perPage = perPage,
                page = page,
            )
        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string() ?: ""
            val json = Json { ignoreUnknownKeys = true }
            val responseError = json.decodeFromString(ResponseError.serializer(), body)
            throw Exception(responseError.message, e)
        }
    }
}