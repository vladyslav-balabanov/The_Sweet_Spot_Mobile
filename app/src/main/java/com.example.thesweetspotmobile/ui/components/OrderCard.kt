package com.example.thesweetspotmobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sweetspot.ui.theme.Brown40
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import com.example.sweetspot.Network.Responses.ProductResponseModel
import com.example.sweetspot.R
import com.example.sweetspot.ui.theme.SweetSpotTheme
import com.example.sweetspot.ui.theme.Tan80

@Composable
fun OrderCard(
    product: ProductResponseModel,
    quantity: Int = 4,
    onClickCard: (
        productId: Int,
        rating: Int,
        comment: String
    ) -> Unit
) {
    var isFirstFocus by remember { mutableStateOf(true) }
    var comment by remember { mutableStateOf(TextFieldValue("Залишити відгук...")) }
    var rating by remember { mutableStateOf(5) }
    val price = product.cost * quantity

    SweetSpotTheme(darkTheme = true) {

        Card(
            modifier = Modifier.size(334.dp, 276.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = Tan80
            )
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(35.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight(400))
                Text(text = "Кількість: $quantity", style = MaterialTheme.typography.bodyLarge,  fontWeight = FontWeight(400))
                Text(text = "Вартість: $price", style = MaterialTheme.typography.bodyLarge,  fontWeight = FontWeight(400))
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Оцінити: ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight(400))
                    RatingBar(rating = rating, onRatingChanged = { rating = it })
                }
                Spacer(modifier = Modifier.height(10.dp))
                BasicTextField(
                    value = comment,
                    onValueChange = { newComment ->
                        if (isFirstFocus && newComment.text.isNotEmpty()) {
                            comment = TextFieldValue("")
                            isFirstFocus = false
                        } else {
                           comment = newComment
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(vertical = 8.dp)
                        .background(Color.White, RectangleShape)
                        .onFocusChanged { focusState ->
                            if (isFirstFocus && focusState.isFocused) {
                                comment = TextFieldValue("")
                                isFirstFocus = false
                            }
                        },

                    textStyle = MaterialTheme.typography.bodySmall,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp),
                        ) {
                            if (comment.text.isEmpty() && !isFirstFocus) {
                                Text(
                                    "Залишити відгук...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }

                            innerTextField()
                        }
                    }
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = Brown40),
                        onClick = {
                            if (comment.text.isNotEmpty() && rating > 0) {
                                onClickCard(product.id, rating, comment.text)
                            }

                        }
                    ) {
                        Text(
                            text = "Відправити",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                    IconButton(
                        onClick = {

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Add to favorites",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RatingBar(rating: Int = 5, onRatingChanged: (Int) -> Unit = {}) {
    Row {
        for (i in 1..5) {
            IconButton(
                modifier = Modifier.size(15.dp),
                onClick = { onRatingChanged(i) }
            ) {
                Icon(
                    painter = if (i <= rating) painterResource(R.drawable.startfilled) else painterResource(R.drawable.startoutline),
                    contentDescription = "Рейтинг $rating",
                    tint = Brown40
                )
            }
        }
    }
}