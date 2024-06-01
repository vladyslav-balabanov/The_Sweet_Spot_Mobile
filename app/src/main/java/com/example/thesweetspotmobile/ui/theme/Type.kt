package com.example.thesweetspotmobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.thesweetspotmobile.R


val Ultra = FontFamily(
        Font(R.font.ultra, FontWeight.Normal)
)
// Set of Material typography styles to start with
val Typography = Typography(
        titleLarge = TextStyle(
                fontFamily = Ultra,
                fontWeight = FontWeight.W400,
                fontSize = 31.sp,
                lineHeight = 39.76.sp,
                textAlign = TextAlign.Center,
                color = Brown40
        ),
        titleMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                lineHeight = 23.44.sp,
                textAlign = TextAlign.Center,
                color = Color.White
        ),
        labelLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
                lineHeight = 18.sp,
                color = Brown40
        ),
        bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center
        ),
        labelSmall = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W500,
                fontSize = 11.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray
        )
)