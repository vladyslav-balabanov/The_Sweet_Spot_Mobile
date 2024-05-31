package com.example.thesweetspotmobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sweetspot.R
import com.example.sweetspot.ui.theme.Brown40

@Preview(showBackground = true)
@Composable
fun CustomHeader(
    onMenuClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp).background(Brown40),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(
            onClick = onMenuClick
        ) {
            Icon(modifier = Modifier.size(35.dp, 31.dp), imageVector = Icons.Filled.Menu, contentDescription = "burgermenu", tint = Color(0xFFEADED1))
        }
        Image(modifier = Modifier.size(79.dp, 56.dp), painter = painterResource(R.drawable.cakelogo), contentDescription = "cakelogo")
    }
}