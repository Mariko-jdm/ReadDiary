package com.mariii.readdiary.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mariii.readdiary.data.local.database.DatabaseProvider
import com.mariii.readdiary.data.repository.BookRepository
import com.mariii.readdiary.ui.components.navigation.BottomBar
import com.mariii.readdiary.ui.screen.AddBookScreen
import com.mariii.readdiary.ui.screen.BookDetailsScreen
import com.mariii.readdiary.ui.screen.HomeScreen
import com.mariii.readdiary.ui.screen.LibraryScreen
import com.mariii.readdiary.ui.screen.StatisticsScreen
import com.mariii.readdiary.ui.viewmodel.BookViewModel
import com.mariii.readdiary.ui.viewmodel.BookViewModelFactory

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val context = LocalContext.current

    val database = DatabaseProvider.getDatabase(context)
    val repository = BookRepository(
        database.bookDao()
    )
    val factory = BookViewModelFactory(repository)

    val bookViewModel: BookViewModel = viewModel(
        factory = factory
    )

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
                HomeScreen(
                    navController = navController,
                    viewModel = bookViewModel
                )
            }

            composable(Screen.Library.route) {
                LibraryScreen(
                    navController = navController,
                    viewModel = bookViewModel
                )
            }

            composable(Screen.Statistics.route) {
                StatisticsScreen()
            }

            composable(Screen.AddBook.route) {
                AddBookScreen(
                    navController = navController,
                    viewModel = bookViewModel
                )
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

                BookDetailsScreen(
                    navController = navController,
                    bookId = bookId,
                    viewModel = bookViewModel
                )


            }
        }
    }
}