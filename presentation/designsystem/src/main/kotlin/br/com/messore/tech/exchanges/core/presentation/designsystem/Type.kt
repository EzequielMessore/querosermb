package br.com.messore.tech.exchanges.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.messore.tech.exchanges.presentation.designsystem.R

val DefaultFont = FontFamily(
    Font(
        resId = R.font.baloo_bhai_2,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.baloo_bhai_2_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.baloo_bhai_2_bold,
        weight = FontWeight.Bold,
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = DefaultFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = DefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = DefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = DefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
)
