package com.example.thesweetspotmobile.ui.home

import FilterCategoryComponent
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.R
import com.example.thesweetspotmobile.ui.components.CustomDrawer
import com.example.thesweetspotmobile.ui.components.CustomHeader
import com.example.thesweetspotmobile.ui.components.CustomMessageBar
import com.example.thesweetspotmobile.ui.components.ProductGrid
import com.example.thesweetspotmobile.ui.components.SearchBar
import com.example.thesweetspotmobile.ui.components.SortDropdownMenu
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.profile.UserProfileViewModel
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    val products by viewModel.products.observeAsState(emptyList())
    //val cart by viewModel.cart.observeAsState()
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
                    content = { paddingValues ->

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Biege80)
                                .padding(paddingValues)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = "Головна",
                                color = Brown40,
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )

                            SearchBar(
                                query = query,
                                onQueryChanged = { newQuery ->
                                    query = newQuery
                                    viewModel.onSearchQueryChanged(newQuery)
                                }
                            )

                            FilterCategoryComponent(updateGood = viewModel::onCategoryChanged)

                            SortDropdownMenu(onSortChanged = viewModel::onSortChanged)

                            ProductGrid(
                                paddingValues = PaddingValues(16.dp),
                                products = products,
                                onAddToCart = { product, quantity ->
                                    viewModel.addToCart(product.id, quantity)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Товар успішно додано до корзини!")
                                    }
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        Button(
                            onClick = { navController.navigate(Screens.Cart.route) },
                            modifier = Modifier.size(70.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Brown40
                            ),
                            shape = CircleShape
                        ) {
                            Image(
                                modifier = Modifier.size(37.dp),
                                painter = painterResource(id = R.drawable.basket),
                                contentDescription = "Cart"
                            )
                        }
                    }
                )
            },
            drawerState = drawerState
        )
    }
}