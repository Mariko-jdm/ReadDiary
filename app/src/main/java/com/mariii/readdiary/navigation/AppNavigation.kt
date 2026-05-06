package com.mariii.readdiary.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import com.mariii.readdiary.ui.screen.*
import com.mariii.readdiary.ui.components.navigation.BottomBar

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen(navController)
            }

            composable(Screen.Library.route) {
                LibraryScreen(navController)
            }

            composable(Screen.Statistics.route) {
                StatisticsScreen()
            }


            composable(
                route = Screen.BookDetails.route,
                arguments = listOf(
                    navArgument("bookId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0

//                BookDetailsScreen(
//                    bookId = bookId,
//                    onBack = { navController.popBackStack() }
//                )
            }
        }
    }
}