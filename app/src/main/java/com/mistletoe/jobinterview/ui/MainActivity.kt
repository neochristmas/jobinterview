package com.mistletoe.jobinterview.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mistletoe.jobinterview.bookmark.BookmarkScreen
import com.mistletoe.jobinterview.qna.QnAListScreen

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }

//        binding = ActivityMainBinding.inflate(layoutInflater).apply {
//            setContentView(root)
//
//            bottomNav.setOnItemSelectedListener(this@MainActivity)
//        }
    }

//    private fun onQnaListClicked(): Boolean {
//        supportFragmentManager.commit {
//            replace(R.id.frame_content, QnAListFragment())
//        }
//        return true
//    }
//
//    private fun onBookmarkClicked(): Boolean {
//        supportFragmentManager.commit {
//            replace(R.id.frame_content, BookmarkFragment())
//        }
//        return true
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
//        R.id.nav_list -> onQnaListClicked()
//        R.id.nav_bookmark -> onBookmarkClicked()
//        else -> false
//    }

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                NavigationBar(
                    containerColor = Color.Transparent
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

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
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "qna",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("qna") { QnAListScreen() }
                composable("bookmark") { BookmarkScreen() }
            }
        }
    }

}
