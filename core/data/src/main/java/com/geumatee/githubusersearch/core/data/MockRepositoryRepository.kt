package com.geumatee.githubusersearch.core.data

import com.geumatee.githubusersearch.core.model.Repository
import javax.inject.Inject

class MockRepositoryRepository @Inject constructor() : RepositoryRepository {
    override suspend fun getRepositories(login: String, perPage: Int, page: Int): List<Repository> {
        val repositories = listOf(
            Repository(
                name = "github",
                description = "GitHub is a code hosting platform for version control and collaboration. It lets you and others work together on projects from anywhere.",
                stargazersCount = 1,
                language = "Kotlin",
                id = 11,
                fork = false,
                htmlUrl = "https://github.com/google/github",
            ),
            Repository(
                name = "google",
                description = "Google's search engine",
                stargazersCount = 1,
                language = "Kotlin",
                id = 12,
                fork = false,
                htmlUrl = "https://github.com/google/google",
            ),
            Repository(
                name = "microsoft",
                description = "Microsoft's search engine",
                stargazersCount = 1,
                language = "Kotlin",
                id = 13,
                fork = false,
                htmlUrl = "https://github.com/google/microsoft",
            ),
            Repository(
                name = "facebook",
                description = "Facebook's search engine",
                stargazersCount = 1,
                language = "Kotlin",
                id = 14,
                fork = false,
                htmlUrl = "https://github.com/google/facebook",
            ),
        )
        if (page > 1 && perPage * page > repositories.size) return emptyList()
        return repositories.subList(0, perPage.coerceAtMost(repositories.size))
    }
}