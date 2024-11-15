package com.geumatee.githubusersearch


import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import com.geumatee.githubusersearch.ui.navigation.model.UserDetailRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class InstrumentedTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private lateinit var navController: TestNavHostController


    @Before
    fun init() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun userSearchTest() {
        composeTestRule.onNodeWithText("There is no user record corresponding to this query.")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("clearSearchText").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("searchTextField").performTextInput("goog")
        composeTestRule.onNodeWithTag("searchTextField").assert(hasText("goog"))
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasText("There is no user record corresponding to this query.")
        )
        composeTestRule.onNodeWithTag("clearSearchText").assertIsDisplayed()
        composeTestRule.onNodeWithText("google").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clearSearchTextTest() {
        composeTestRule.onNodeWithTag("searchTextField").performTextInput("goog")
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasText("There is no user record corresponding to this query.")
        )
        composeTestRule.onNodeWithTag("clearSearchText").performClick()
        composeTestRule.waitUntilAtLeastOneExists(
            matcher = hasText("There is no user record corresponding to this query.")
        )
        composeTestRule.onNodeWithTag("searchTextField").assert(hasText(""))
        composeTestRule.onNodeWithTag("clearSearchText")
            .assertIsNotDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickUser_navigateUserDetailTest() {
        composeTestRule.onNodeWithTag("searchTextField").performTextInput("goog")
        composeTestRule.onNodeWithTag("searchTextField").assert(hasText("goog"))
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasText("There is no user record corresponding to this query.")
        )
        composeTestRule.onNodeWithText("google").performClick()
        assertTrue(
            navController.currentBackStackEntry?.destination?.hasRoute<UserDetailRoute>() ?: false
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun userDetail_showRepository_openRepositoryUrlTest() {
        composeTestRule.onNodeWithTag("searchTextField").performTextInput("goog")
        composeTestRule.onNodeWithTag("searchTextField").assert(hasText("goog"))
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasText("There is no user record corresponding to this query.")
        )
        composeTestRule.onNodeWithText("google").performClick()
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasText("There is no user's repository record.")
        )
        composeTestRule.onNodeWithText("github").assertIsDisplayed()
        composeTestRule.onNodeWithText("github").performClick()
        Thread.sleep(100)

        Intents.intended(allOf(
            IntentMatchers.hasAction("android.intent.action.VIEW"),
            IntentMatchers.hasData("https://github.com/google/github"),
            hasCustomTabsExtras()
        ))
    }

    private fun hasCustomTabsExtras(): Matcher<Intent> {
        return object : org.hamcrest.TypeSafeMatcher<Intent>() {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("has CustomTabs extras")
            }

            override fun matchesSafely(intent: Intent): Boolean {
                val extras = intent.extras ?: return false
                return extras.containsKey("android.support.customtabs.extra.SESSION") &&
                        extras.containsKey("androidx.browser.customtabs.extra.SHARE_STATE")
            }
        }
    }
}