package com.example.thesweetspotmobile.ui.order

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import com.example.thesweetspotmobile.ui.components.CustomDrawer
import com.example.thesweetspotmobile.ui.components.CustomHeader
import com.example.thesweetspotmobile.ui.components.CustomMessageBar
import com.example.thesweetspotmobile.ui.components.HeaderWithBackButton
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.profile.UserProfileViewModel
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.Brown80
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Order(
        navController: NavController,
        cartId: Int,
        totalSum: Float,
        orderViewModel: OrderViewModel = hiltViewModel(),
        userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {


    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }

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
                                        navController.navigate(Screens.History.route)
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
                            snackbarHost = { CustomMessageBar(snackbarHostState) },
                            content = {

                                Column(modifier = Modifier.fillMaxSize()) {
                                    Spacer(modifier = Modifier.height(100.dp))
                                    HeaderWithBackButton(title = "Замовлення №${cartId}", onBackClick = {
                                        navController.popBackStack()
                                    })
                                    Spacer(modifier = Modifier.height(50.dp))
                                    Text(modifier = Modifier.padding(start = 20.dp), text = "Вартість замовлення: $totalSum грн", textAlign = TextAlign.Start, color = Color.DarkGray)
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Text(modifier = Modifier.padding(start = 20.dp), text = "Адреса доставки:", textAlign = TextAlign.Start, color = Color.DarkGray)
                                    Spacer(modifier = Modifier.height(30.dp))
                                    TextField(
                                            value = orderViewModel.area,
                                            onValueChange = { orderViewModel.area = it },
                                            placeholder = { Text("Район міста Харків") },
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                    containerColor = Color(0xFFEFEDE8),
                                                    unfocusedTextColor = Color.Black,
                                                    focusedTextColor = Color.Black,
                                                    cursorColor = Color.Black,
                                                    focusedIndicatorColor = Color.Black,
                                                    unfocusedIndicatorColor = Color.Black
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextField(
                                            value = orderViewModel.street,
                                            onValueChange = { orderViewModel.street = it },
                                            placeholder = { Text("Вулиця") },
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                    containerColor = Color(0xFFEFEDE8),
                                                    unfocusedTextColor = Color.Black,
                                                    focusedTextColor = Color.Black,
                                                    cursorColor = Color.Black,
                                                    focusedIndicatorColor = Color.Black,
                                                    unfocusedIndicatorColor = Color.Black
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextField(
                                            value = orderViewModel.houseNumber,
                                            onValueChange = { orderViewModel.houseNumber = it },
                                            placeholder = { Text("Дім, блок, квартира") },
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                    containerColor = Color(0xFFEFEDE8),
                                                    unfocusedTextColor = Color.Black,
                                                    focusedTextColor = Color.Black,
                                                    cursorColor = Color.Black,
                                                    focusedIndicatorColor = Color.Black,
                                                    unfocusedIndicatorColor = Color.Black
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(30.dp))

                                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                                        Button(
                                                onClick = { navController.navigate(Screens.Home.route) },
                                                modifier = Modifier.weight(1f).padding(end = 8.dp),
                                                shape = MaterialTheme.shapes.medium,
                                                colors = ButtonColors(
                                                        containerColor = com.example.thesweetspotmobile.ui.theme.Button,
                                                        contentColor = Color.White,
                                                        disabledContentColor = Color.Transparent,
                                                        disabledContainerColor = Color.Transparent
                                                )
                                        ) {
                                            Text("Скасувати", style = MaterialTheme.typography.labelMedium, color = Color.White)
                                        }
                                        Button(
                                                onClick = {
                                                    orderViewModel.submitOrder(cartId)
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar("Замовлення успішно відправлено!")
                                                    }
                                                    navController.navigate(Screens.Home.route)
                                                },
                                                modifier = Modifier.weight(1f).padding(start = 8.dp),
                                                shape = MaterialTheme.shapes.medium,
                                                colors = ButtonColors(
                                                        containerColor = Brown40,
                                                        contentColor = Color.White,
                                                        disabledContentColor = Color.Transparent,
                                                        disabledContainerColor = Color.Transparent
                                                )
                                        ) {
                                            Text("Сплатити",style = MaterialTheme.typography.labelMedium, color = Color.White)
                                        }
                                    }
                                }
                            }
                    )
                },
                drawerState = drawerState
        )
    }
}