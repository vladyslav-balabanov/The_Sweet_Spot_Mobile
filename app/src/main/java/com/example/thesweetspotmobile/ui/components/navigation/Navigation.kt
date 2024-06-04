package com.example.thesweetspotmobile.ui.components.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thesweetspotmobile.Utils.TokenManager
import com.example.thesweetspotmobile.ui.auth.Auth
import com.example.thesweetspotmobile.ui.cart.Cart
import com.example.thesweetspotmobile.ui.history.History
import com.example.thesweetspotmobile.ui.historydetail.HistoryDetails
import com.example.thesweetspotmobile.ui.home.Home
import com.example.thesweetspotmobile.ui.order.Order
import com.example.thesweetspotmobile.ui.profile.Profile

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    var startDestination = Screens.Auth.route
    val tokenManager = TokenManager(LocalContext.current)
    if (tokenManager.fetchToken() != null) {
        startDestination = Screens.Home.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None},
        popEnterTransition = { EnterTransition.None},
        popExitTransition = { ExitTransition.None}
    ) {
        composable(Screens.Auth.route) {
            Auth(navController = navController)
        }
        composable(Screens.Profile.route) {
            Profile(navController = navController)
        }
        composable(Screens.Home.route) {
            Home(navController = navController)
        }
        composable(Screens.Cart.route) {
            Cart(navController = navController)
        }
        composable(
            route = Screens.Order.route + "?cartId={cartId}&totalSum={totalSum}",
            arguments = listOf(
                navArgument("cartId") {
                    type = NavType.IntType
                },
                navArgument("totalSum") {
                    type = NavType.FloatType
                }
            )
        ) { backStackEntry ->
            val cartId = backStackEntry.arguments?.getInt("cartId")
            val totalSum = backStackEntry.arguments?.getFloat("totalSum")
            if (cartId != null) {
                Order(
                    navController = navController,
                    cartId = cartId,
                    totalSum = totalSum ?: 0f
                )
            }
        }
        composable(Screens.History.route) {
            History(navController = navController)
        }
        composable("${Screens.HistoryDetails.route}/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            if (orderId != null) {
                HistoryDetails(navController = navController, orderId = orderId)
            }
        }

    }
}