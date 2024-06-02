package com.example.thesweetspotmobile.ui.historydetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.Network.Responses.OrderResponseModel
import com.example.thesweetspotmobile.Utils.OrderStatusEnum
import com.example.thesweetspotmobile.ui.components.CustomDrawer
import com.example.thesweetspotmobile.ui.components.CustomHeader
import com.example.thesweetspotmobile.ui.components.CustomMessageBar
import com.example.thesweetspotmobile.ui.components.HeaderWithBackButton
import com.example.thesweetspotmobile.ui.components.OrderCard
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.profile.UserProfileViewModel
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryDetails(
    navController: NavController,
    orderId: String,
    historyDetailsViewModel: HistoryDetailsViewModel = hiltViewModel(),
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val orderDetails = historyDetailsViewModel.orderDetails.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(orderId) {
        historyDetailsViewModel.loadOrderDetails(orderId)
    }
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
                        LazyColumn(
                            modifier = Modifier.padding(top = 70.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                HeaderWithBackButton(title = "Замовлення №$orderId", onBackClick = {
                                    navController.popBackStack()
                                })
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            orderDetails.value?.cart?.cartProducts?.let { cartProducts ->
                                items(cartProducts) { orderProduct ->
                                    orderProduct.product?.let { it1 ->
                                        OrderCard(product = it1, quantity = orderProduct.quantity, onClickCard = { productId, rating, comment ->
                                            historyDetailsViewModel.postReview(productId = productId, grade = rating, text = comment)
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Відгук успішно додано")
                                            }
                                        }, onIconClick = {
                                                productId, rating, comment  ->
                                            val shareIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, "Дивись я купив продукт $productId у додатку SweetSpot: Я поставив йому таку оцінку$rating\nМій коментар: $comment\nРаджу теж спробувати!")
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(shareIntent, "Поділитися через:"))
                                        })
                                    }
                                    Spacer(modifier = Modifier.height(15.dp))
                                }
                                item {
                                    SummaryDetails(order = orderDetails.value)
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummaryDetails(order: OrderResponseModel?) {
    order?.let {
        val totalPrice = it.cart.cartProducts?.sumOf { product ->
            product.product?.cost?.times(product.quantity) ?: 0.0
        } ?: 0.0

        val formattedDate = run {
            val parser = DateTimeFormatter.ISO_DATE_TIME
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val dateTime = LocalDateTime.parse(order.createdDate, parser)
            dateTime.format(formatter)
        }

        val orderStatus = OrderStatusEnum.fromInt(order.status).status

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.padding(start = 15.dp).size(143.dp, 38.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(2.dp, Brown40)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Сплачено: ${totalPrice} грн",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight(400),
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(modifier = Modifier.padding(start = 15.dp), text = "Дата замовлення: $formattedDate", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight(500))
        Spacer(modifier = Modifier.height(15.dp))
        Text(modifier = Modifier.padding(start = 15.dp), text = "Статус замовлення: ${orderStatus}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight(600))
        Spacer(modifier = Modifier.height(15.dp))
    }
}