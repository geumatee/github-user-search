package com.geumatee.githubusersearch

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.geumatee.githubusersearch.ui.compose.userdetail.UserDetailRoute
import com.geumatee.githubusersearch.ui.compose.usersearch.UserSearchRoute
import com.geumatee.githubusersearch.ui.navigation.model.UserDetailRoute
import com.geumatee.githubusersearch.ui.navigation.model.UserSearchRoute
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
                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = UserSearchRoute) {
        composable<UserSearchRoute> {
            UserSearchRoute(
                onClick = { id, login, avatarUrl ->
                    navController.navigate(
                        UserDetailRoute(
                            id = id,
                            login = login,
                            avatarUrl = avatarUrl,
                        )
                    )
                },
            )
        }
        composable<UserDetailRoute> { backStackEntry ->
            val userDetailRoute = backStackEntry.toRoute<UserDetailRoute>()
            UserDetailRoute(
                id = userDetailRoute.id,
                login = userDetailRoute.login,
                avatarUrl = userDetailRoute.avatarUrl,
                navigateBack = { navController.popBackStack() },
                onClick = { repositoryUrl ->
                    val intent = CustomTabsIntent.Builder()
                        .build()
                    intent.launchUrl(context, Uri.parse(repositoryUrl))
                }
            )
        }
    }
}