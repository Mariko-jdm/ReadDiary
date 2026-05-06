package com.mariii.readdiary.ui.components.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.mariii.readdiary.navigation.Screen


@Composable
fun BottomBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route)
                launchSingleTop = true}
                      },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Главная") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Library.route,
            onClick = { navController.navigate(Screen.Library.route) {
                popUpTo(Screen.Home.route)
                launchSingleTop = true}
            },
            icon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
            label = { Text("Книги") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Statistics.route,
            onClick = { navController.navigate(Screen.Statistics.route) {
                popUpTo(Screen.Home.route)
                launchSingleTop = true}
            },
            icon = { Icon(Icons.Default.BarChart, contentDescription = null) },
            label = { Text("Статистика") }
        )
    }
}