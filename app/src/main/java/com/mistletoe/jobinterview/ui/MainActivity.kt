package com.mistletoe.jobinterview.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.mistletoe.jobinterview.bookmark.BookmarkScreen
import com.mistletoe.jobinterview.qna.QnAListScreen
import com.mistletoe.jobinterview.qna.QnAListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()  // 한 번만 생성
                MainScreen(navController)
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavHostController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val showBottomBar = currentRoute == "qna" || currentRoute == "bookmark"

        Scaffold(
            bottomBar = {
                if (showBottomBar) BottomNavigationBar(navController, currentRoute!!)
            }
        ) { innerPadding ->
            AppNav(innerPadding, navController)
        }
    }

    @Composable
    private fun BottomNavigationBar(navController: NavHostController, currentRoute: String) {
        HorizontalDivider(
            color = Color.Black.copy(alpha = 0.1f),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        NavigationBar(
            containerColor = Color.Transparent
        ) {
            NavigationBarItem(
                selected = currentRoute == "qna",
                onClick = {
                    if (navController.currentDestination?.route != "qna") { // 중복 화면 스택 쌓임 방지
                        navController.navigate("qna") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(Icons.Default.Menu, contentDescription = null) },
                label = { Text("QnA") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Blue,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = currentRoute == "bookmark",
                onClick = {
                    if (navController.currentDestination?.route != "bookmark") {
                        navController.navigate("bookmark") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                label = { Text("Bookmark") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Blue,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun AppNav(innerPadding: PaddingValues, navController: NavHostController) {
        val viewModel: QnAListViewModel = hiltViewModel()

        AnimatedNavHost(
            navController = navController,
            startDestination = "qna",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("qna") {
                QnAListScreen(navController, viewModel)
            }
            composable("bookmark") { BookmarkScreen() }
            composable(
                route = "add/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                AddScreen(navController, category)
            }
            composable("practice") {
                PracticeScreen(navController, viewModel)
            }
        }
    }

}