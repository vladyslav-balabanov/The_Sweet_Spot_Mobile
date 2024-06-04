package com.example.thesweetspotmobile.ui.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.Network.Requests.CartRequest
import com.example.thesweetspotmobile.ui.components.CartProductGrid
import com.example.thesweetspotmobile.ui.components.CustomDrawer
import com.example.thesweetspotmobile.ui.components.CustomHeader
import com.example.thesweetspotmobile.ui.components.HeaderWithBackButton
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.profile.UserProfileViewModel
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.Button
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Cart(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val cart by cartViewModel.cart.observeAsState()
    val totalSum = cart?.cartProducts?.sumOf { (it.product?.cost ?: 0.0) * it.quantity } ?: 0.0
    val cartId = cart?.cartProducts?.firstOrNull()?.cartId
    SweetSpotTheme(dynamicColor = false, darkTheme = true) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    CustomDrawer(
                        profileViewModel = userProfileViewModel,
                        onPersonalClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Cart.route)
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
                            }
                        },
                        onHistoryClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.History.route)
                            }
                        }
                    )
                }
            },
            content = {
                Scaffold(
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
                    content = { paddingValues ->
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(Biege80)
                            .padding(paddingValues)
                            .verticalScroll(
                                rememberScrollState()
                            )) {
                            HeaderWithBackButton(title = "Кошик", onBackClick = {
                                scope.launch {
                                    drawerState.close()
                                    navController.popBackStack()
                                }
                            })
                            Spacer(modifier = Modifier.height(20.dp))
                            if (cart?.cartProducts.isNullOrEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(top = 100.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(modifier = Modifier
                                        .size(230.dp, 79.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .border(BorderStroke(2.dp, Brown40), shape = MaterialTheme.shapes.medium)
                                    ) {
                                        Text("Поки що нічого немає! Зробіть замовлення прямо зараз!",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Brown40
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(180.dp))
                                    Button(
                                        modifier = Modifier.size(326.dp, 53.dp),
                                        onClick = {
                                            navController.navigate(Screens.Home.route)
                                        },
                                        shape = MaterialTheme.shapes.medium,
                                        colors = ButtonColors(
                                            containerColor = Brown40,
                                            contentColor = Color.White,
                                            disabledContentColor = Color.Transparent,
                                            disabledContainerColor = Color.Transparent
                                        )
                                    ) {
                                        Text(
                                            text = "На головну",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.White
                                        )
                                    }

                                }
                            } else {
                                CartProductGrid(
                                    paddingValues = PaddingValues(16.dp),
                                    cartProducts = cart!!.cartProducts!!,
                                    onQuantityChange = { productId, quantity ->
                                        cartViewModel.updateCartProduct(
                                            CartRequest(
                                                productId,
                                                quantity
                                            )
                                        )
                                    },
                                    onRemoveProduct = { productId ->
                                        cartViewModel.removeProduct(productId)
                                    }
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        modifier = Modifier.size(296.dp, 43.dp),
                                        onClick = {
                                            cartViewModel.deleteCart()
                                        },
                                        shape = MaterialTheme.shapes.medium,
                                        colors = ButtonColors(
                                            containerColor = Button,
                                            contentColor = Color.White,
                                            disabledContentColor = Color.Transparent,
                                            disabledContainerColor = Color.Transparent
                                        )
                                    ) {
                                        Text(
                                            text = "Очистити кошик",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        modifier = Modifier.size(296.dp, 43.dp),
                                        onClick = {
                                            navController.navigate(Screens.Order.route + "?cartId=$cartId&totalSum=$totalSum")
                                        },
                                        shape = MaterialTheme.shapes.medium,
                                        colors = ButtonColors(
                                            containerColor = Brown40,
                                            contentColor = Color.White,
                                            disabledContentColor = Color.Transparent,
                                            disabledContainerColor = Color.Transparent
                                        )
                                    ) {
                                        Text(
                                            text = "Перейти до сплати: $totalSum грн",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        )
    }
}