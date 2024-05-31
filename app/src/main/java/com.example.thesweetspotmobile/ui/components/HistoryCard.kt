package com.example.thesweetspotmobile.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sweetspot.Network.Responses.CartProductResponseModel
import com.example.sweetspot.Network.Responses.CartResponseModel
import com.example.sweetspot.Network.Responses.OrderResponseModel
import com.example.sweetspot.Network.Responses.ProductResponseModel
import com.example.sweetspot.Network.Responses.UserResponseModel
import com.example.sweetspot.Utils.OrderStatusEnum
import com.example.sweetspot.ui.theme.Brown40
import com.example.sweetspot.ui.theme.SweetSpotTheme
import com.example.sweetspot.ui.theme.Tan80
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryCard(order: OrderResponseModel, onCardClick: (Int) -> Unit) {
    val totalPrice = order.cart.cartProducts?.sumOf { it.product!!.cost * it.quantity }
    val orderStatus = OrderStatusEnum.fromInt(order.status).status

    val formattedDate = run {
        val parser = DateTimeFormatter.ISO_DATE_TIME
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val dateTime = LocalDateTime.parse(order.createdDate, parser)
        dateTime.format(formatter)
    }
    SweetSpotTheme(darkTheme = true) {
        Card(
            modifier = Modifier.size(318.dp, 144.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Tan80
            )
        ) {
            Row(
                modifier = Modifier.padding(top = 5.dp, start = 10.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Замовлення №${order.id}", style = MaterialTheme.typography.labelMedium)
                IconButton(
                    onClick = {
                        onCardClick(order.id)
                    }
                ) {
                    Icon(modifier = Modifier.size(21.dp, 18.dp), imageVector = Icons.Filled.ArrowForward, contentDescription = "details")
                }
            }
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
                    Text(text = "Сплачено: $totalPrice грн", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight(400), color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(modifier = Modifier.padding(start = 15.dp), text = "Дата замовлення: $formattedDate", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight(500))
            Spacer(modifier = Modifier.height(5.dp))

            Text(modifier = Modifier.padding(start = 15.dp), text = "Статус замовлення: $orderStatus", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight(600))
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}