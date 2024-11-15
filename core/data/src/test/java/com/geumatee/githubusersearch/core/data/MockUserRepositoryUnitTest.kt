package com.geumatee.githubusersearch.core.data

import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MockUserRepositoryUnitTest {
    @Test
    fun searchUser_isCorrect() = runTest {
        val userRepository = MockUserRepository()
        val userSearchResponse = userRepository.searchUsers("goo", 10, 1)
        assertEquals(userSearchResponse.totalCount, 1)
        assertEquals(userSearchResponse.items[0].id, 1)
        assertEquals(userSearchResponse.items[0].login, "google")
    }

    @Test
    fun searchUsers_isCorrect() = runTest {
        val userRepository = MockUserRepository()
        val userSearchResponse = userRepository.searchUsers("oo", 2, 1)
        assertEquals(userSearchResponse.totalCount, 2)
        assertEquals(userSearchResponse.items[0].id, 1)
        assertEquals(userSearchResponse.items[0].login, "google")
        assertEquals(userSearchResponse.items[1].id, 2)
        assertEquals(userSearchResponse.items[1].login, "facebook")
    }

    @Test
    fun searchUsers_withPageSize_isCorrect() = runTest {
        val userRepository = MockUserRepository()
        val userSearchResponse = userRepository.searchUsers("oo", 1, 1)
        assertEquals(userSearchResponse.totalCount, 1)
        assertEquals(userSearchResponse.items[0].id, 1)
        assertEquals(userSearchResponse.items[0].login, "google")
    }

    @Test
    fun searchUser_isIncorrect() = runTest {
        val userRepository = MockUserRepository()
        val userSearchResponse = userRepository.searchUsers("google", 1, 1)
        assertNotEquals(userSearchResponse.totalCount, 2)
        assertNotEquals(userSearchResponse.items[0].id, 2)
        assertNotEquals(userSearchResponse.items[0].login, "facebook")
    }

    @Test
    fun getUserDetail_isCorrect() = runTest {
        val userRepository = MockUserRepository()
        val userDetail = userRepository.getUserDetail("google")
        assertEquals(userDetail.id, 1)
        assertEquals(userDetail.login, "google")
        assertEquals(userDetail.name, "Google")
        assertEquals(userDetail.followers, 46292)
    }

    @Test
    fun getUserDetail_isIncorrect() = runTest {
        val userRepository = MockUserRepository()
        val userDetail = userRepository.getUserDetail("facebook")
        assertNotEquals(userDetail.id, 1)
        assertNotEquals(userDetail.login, "google")
        assertNotEquals(userDetail.name, "Google")
        assertNotEquals(userDetail.followers, 46292)
    }
}