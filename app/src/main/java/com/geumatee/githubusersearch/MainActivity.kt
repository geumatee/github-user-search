package com.geumatee.githubusersearch

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geumatee.githubusersearch.ui.compose.userdetail.UserDetailRoute
import com.geumatee.githubusersearch.ui.compose.usersearch.UserSearchRoute
import com.geumatee.githubusersearch.ui.theme.GitHubUserSearchTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUserSearchTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "user_search") {
                    composable("user_search") {
                        UserSearchRoute(
                            onClick = { login ->
                                navController.navigate("user_detail/$login")
                            })
                    }
                    composable("user_detail/{login}") {
                        val login = it.arguments?.getString("login") ?: return@composable
                        UserDetailRoute(
                            login = login,
                            navigateBack = { navController.popBackStack() },
                            onClick = { repositoryUrl ->
                                val intent = CustomTabsIntent.Builder()
                                    .build()
                                intent.launchUrl(this@MainActivity, Uri.parse(repositoryUrl))
                            }
                        )
                    }
                }
            }
        }
    }
}