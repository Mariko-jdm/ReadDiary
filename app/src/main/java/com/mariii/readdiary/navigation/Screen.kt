package com.mariii.readdiary.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")
    object Library : Screen("library")
    object Statistics : Screen("statistics")

    object BookDetails : Screen("book_details")
}