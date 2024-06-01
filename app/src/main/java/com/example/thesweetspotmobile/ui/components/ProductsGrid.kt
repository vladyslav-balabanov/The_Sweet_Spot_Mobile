package com.example.thesweetspotmobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thesweetspotmobile.Network.Responses.CartProductResponseModel
import com.example.thesweetspotmobile.Network.Responses.ProductResponseModel
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import com.google.accompanist.flowlayout.FlowRow



@Composable
fun ProductGrid(
    paddingValues: PaddingValues,
    products: List<ProductResponseModel>,
    onAddToCart: (ProductResponseModel, Int) -> Unit
) {
    SweetSpotTheme(darkTheme = true) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val itemWidth = (screenWidth - 53.dp) / 2
        FlowRow(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp, bottom = 16.dp),
            mainAxisSpacing = 20.dp,
            crossAxisSpacing = 20.dp,
        ) {
            products.forEach { product ->
                GoodCard(
                    product = product,
                    onAddToCart = { quantity -> onAddToCart(product, quantity) },
                    modifier = Modifier.width(itemWidth)
                )
            }
        }
    }
}

@Composable
fun CartProductGrid(
    paddingValues: PaddingValues,
    cartProducts: List<CartProductResponseModel>,
    onQuantityChange: (Int, Int) -> Unit,
    onRemoveProduct: (Int) -> Unit
) {
    SweetSpotTheme(darkTheme = true) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val itemWidth = (screenWidth - 53.dp) / 2
        FlowRow(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp, bottom = 16.dp),
            mainAxisSpacing = 20.dp,
            crossAxisSpacing = 20.dp,
        ) {
            cartProducts.forEach { product ->
                CartGoodCard(
                    cartProduct = product,
                    onQuantityChange = { newQuantity ->
                        onQuantityChange(product.productId, newQuantity)
                    },
                    onRemove = {
                        onRemoveProduct(product.productId)
                    },
                    modifier = Modifier.width(itemWidth)
                )
            }
        }
    }
}
