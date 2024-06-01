package com.example.thesweetspotmobile.ui.components.navigation

sealed class Screens(val route: String) {
    object Auth : Screens("auth")
    object Profile : Screens("profile")
    object Home : Screens("home")
    object Cart : Screens("cart")
    object Order : Screens("order")
    object History : Screens("history")
    object HistoryDetails : Screens("history_details")
}