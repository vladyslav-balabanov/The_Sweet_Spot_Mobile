package com.example.thesweetspotmobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesweetspotmobile.Network.Responses.CartProductResponseModel
import com.example.thesweetspotmobile.Network.Responses.ProductResponseModel
import com.example.thesweetspotmobile.R
import com.example.thesweetspotmobile.Utils.Decoder
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import com.example.thesweetspotmobile.ui.theme.Tan80
import com.example.thesweetspotmobile.ui.theme.Button

@Composable
fun GoodCard(
    product: ProductResponseModel,
    onAddToCart: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableStateOf(1) }
    SweetSpotTheme(darkTheme = true, dynamicColor = false) {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Tan80)
        ) {
            GoodCardContent(
                name = product.name,
                unitPrice = product.cost,
                imageBase64 = product.image,
                showCloseButton = false,
                showAddToCartButton = true,
                onQuantityChange = { newQuantity -> quantity = newQuantity },
                quantity = quantity,
                onAddToCart = { onAddToCart(quantity) },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun CartGoodCard(
    cartProduct: CartProductResponseModel,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    SweetSpotTheme(darkTheme = true, dynamicColor = false) {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Tan80)
        ) {
            GoodCardContent(
                name = cartProduct.product?.name ?: "Неизвестный товар",
                unitPrice = cartProduct.product?.cost ?: 0.0,
                quantity = cartProduct.quantity,
                imageBase64 = cartProduct.product?.image ?: "",
                showCloseButton = true,
                showAddToCartButton = false,
                onQuantityChange = onQuantityChange,
                onRemove = onRemove,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun GoodCardContent(
    name: String,
    imageBase64: String = "",
    showCloseButton: Boolean = false,
    showAddToCartButton: Boolean = true,
    onQuantityChange: (Int) -> Unit = {},
    onAddToCart: (Int) -> Unit = {},
    onRemove: () -> Unit = {},
    unitPrice: Double = 70.0,
    quantity: Int = 1,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageBitmap: ImageBitmap = remember(imageBase64) {
        Decoder.decodeImage(
            context = context,
            base64 = imageBase64,
            defaultImage = R.drawable.profileimage
        )
    }

    SweetSpotTheme(darkTheme = true, dynamicColor = false) {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Tan80)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                if (showCloseButton) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd),
                            onClick = onRemove
                        ) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                        }
                    }
                }
                GoodImage(imageBitmap)
                Spacer(modifier = Modifier.height(5.dp))
                GoodDetails(
                    name = name,
                    unitPrice = unitPrice,
                    quantity = quantity
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (!showAddToCartButton) {
                    Spacer(modifier = Modifier.height(25.dp))
                }
                QuantitySelector(
                    quantity = quantity,
                    onIncrease = { onQuantityChange(quantity + 1) },
                    onDecrease = { if (quantity > 0) onQuantityChange(quantity - 1) }
                )
                Spacer(modifier = Modifier.height(15.dp))
                if (showAddToCartButton) {
                    Button(
                        onClick = { onAddToCart(quantity) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Brown40)
                    ) {
                        Text(
                            text = "Додати до кошика",
                            fontSize = 8.sp,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            softWrap = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoodImage(imageBitmap: ImageBitmap) {
    Image(
        bitmap = imageBitmap,
        contentDescription = "goodImage",
        modifier = Modifier
            .size(120.dp)
            .clip(MaterialTheme.shapes.extraLarge),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun GoodDetails(
    name: String = "Тістечко Брауні, 1шт - 80 гр", unitPrice: Double, quantity: Int
) {
    val totalPrice = unitPrice * quantity

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.sp,
            maxLines = 1,
            softWrap = true
        )
        Text(
            text = "Ціна: ${totalPrice} грн",
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.sp
        )
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Button),
        modifier = Modifier.size(72.dp, 23.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                onDecrease()
            }, modifier = Modifier.size(23.dp)) {
                Icon(
                    painter = painterResource(R.drawable.minus),
                    contentDescription = "Decrease",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = {
                onIncrease()
            }, modifier = Modifier.size(23.dp)) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = "Increase",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}