package com.mariii.readdiary.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mariii.readdiary.navigation.Screen
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Primary
import com.mariii.readdiary.ui.theme.Surface

@Composable
fun BottomBar(
    navController: NavController
) {

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value?.destination?.route

    val navigationItemColors = NavigationBarItemDefaults.colors(

        indicatorColor = Primary,

        selectedIconColor = OnSurface,
        selectedTextColor = OnSurface,

        unselectedIconColor = OnSurface.copy(alpha = 0.7f),
        unselectedTextColor = OnSurface.copy(alpha = 0.7f)
    )

    NavigationBar(
        containerColor = Surface
    ) {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,

            onClick = {
                navController.navigate(Screen.Home.route) {

                    popUpTo(Screen.Home.route)

                    launchSingleTop = true
                }
            },

            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null
                )
            },

            label = {
                Text(stringResource(com.mariii.readdiary.R.string.home))
            },

            colors = navigationItemColors
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Library.route,

            onClick = {
                navController.navigate(Screen.Library.route) {

                    popUpTo(Screen.Home.route)

                    launchSingleTop = true
                }
            },

            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null
                )
            },

            label = {
                Text(stringResource(com.mariii.readdiary.R.string.books))
            },

            colors = navigationItemColors
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Statistics.route,

            onClick = {
                navController.navigate(Screen.Statistics.route) {

                    popUpTo(Screen.Home.route)

                    launchSingleTop = true
                }
            },

            icon = {
                Icon(
                    Icons.Default.BarChart,
                    contentDescription = null
                )
            },

            label = {
                Text(stringResource(com.mariii.readdiary.R.string.statistics))
            },

            colors = navigationItemColors
        )
    }
}