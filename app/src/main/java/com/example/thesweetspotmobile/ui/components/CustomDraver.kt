package com.example.thesweetspotmobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.thesweetspotmobile.R
import com.example.thesweetspotmobile.Network.Responses.UserResponseModel
import com.example.thesweetspotmobile.Utils.Decoder
import com.example.thesweetspotmobile.ui.profile.UserProfileViewModel
import com.example.thesweetspotmobile.ui.theme.Drawer
import com.example.thesweetspotmobile.ui.theme.DrawerHeaderColor
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme

@Composable
fun CustomDrawer(
    profileViewModel: UserProfileViewModel,
    onPersonalClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    val user by profileViewModel.user.observeAsState()

    Column(
        modifier = Modifier
            .width(243.dp)
            .fillMaxHeight()
            .background(Drawer)
    ) {
        DrawerHeader(user)
        DrawerItem(text = "Особистий кабінет", onClick = onPersonalClick)
        DrawerItem(text = "Головна", onClick = onHomeClick)
        DrawerItem(text = "Кошик", onClick = onCartClick)
        DrawerItem(text = "Історія замовлень", onClick = onHistoryClick)
    }
}

@Composable
fun DrawerHeader(user: UserResponseModel?) {
    SweetSpotTheme(
        darkTheme = true
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(34.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(DrawerHeaderColor)
                .padding(16.dp)

        ) {
            val userImage = user?.image?.let { Decoder.decodeSimpleBase64(it) }
            if (userImage != null) {
                Image(
                    bitmap = userImage.asImageBitmap(),
                    modifier = Modifier.size(70.dp),
                    contentDescription = "Drawer Header Image",
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profileimage),
                    modifier = Modifier.size(70.dp),
                    contentDescription = "Drawer Header Image",
                )
            }
            Text(text = user?.name ?: "Помилка", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun DrawerItem(
    text: String = "Test",
    onClick: () -> Unit = {}
) {
    SweetSpotTheme(darkTheme = true) {

        Button(
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Drawer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )
                HorizontalDivider(
                    thickness = 3.dp,
                    color = Color(0xFFEADED1),
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}