package com.example.thesweetspotmobile.ui.history

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.ui.components.CustomDrawer
import com.example.thesweetspotmobile.ui.components.CustomHeader
import com.example.thesweetspotmobile.ui.components.HeaderWithBackButton
import com.example.thesweetspotmobile.ui.components.HistoryCard
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.profile.UserProfileViewModel
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun History(navController: NavController
            ,viewModel: OrderHistoryViewModel = hiltViewModel()
            ,userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.observeAsState(emptyList())
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    SweetSpotTheme(darkTheme = true, dynamicColor = false) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    CustomDrawer(
                        profileViewModel = userProfileViewModel,
                        onPersonalClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Profile.route)
                            }
                        },
                        onHomeClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Home.route)
                            }
                        },
                        onCartClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Cart.route)
                            }
                        },
                        onHistoryClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            },
            content = {
                Scaffold(
                    modifier = Modifier.background(Biege80),
                    topBar = {
                        CustomHeader(onMenuClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        })
                    },
                    content = {
                        LazyColumn(
                            modifier = Modifier.padding(top = 70.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                HeaderWithBackButton(title = "Історія замовлень", onBackClick = {
                                    navController.popBackStack()
                                })
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            items(orders) { order ->
                                HistoryCard(order = order, onCardClick = { orderId ->
                                    navController.navigate("${Screens.HistoryDetails.route}/$orderId")
                                })
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    }
                )
            },
            drawerState = drawerState
        )
    }
}