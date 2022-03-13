package com.lukakordzaia.subscriptionmanager.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val titleStyle = TextStyle(
    fontFamily = mainLight,
    fontWeight = FontWeight.Bold,
    color = _1F1F1F,
    fontSize = 20.sp
)

val bottomNavLabelStyle = TextStyle(
    fontFamily = mainLight,
    fontWeight = FontWeight.Bold
)

val generalButtonStyle = TextStyle(
    fontFamily = mainBold,
    fontWeight = FontWeight.Light,
    color = _FFFFFF,
    fontSize = 18.sp
)

val smallButtonStyle = TextStyle(
    fontFamily = mainLight,
    fontWeight = FontWeight.Bold,
    color = _FFFFFF
)