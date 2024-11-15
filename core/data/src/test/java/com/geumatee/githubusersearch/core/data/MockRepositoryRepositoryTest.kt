package com.geumatee.githubusersearch.core.data

import kotlinx.coroutines.test.runTest
import org.junit.Test

class MockRepositoryRepositoryTest {
    @Test
    fun getRepositories_isCorrect() = runTest {
        val repositoryRepository = MockRepositoryRepository()
        val repositories = repositoryRepository.getRepositories("google", 2, 1)
        assert(repositories.size <= 2)
    }
}