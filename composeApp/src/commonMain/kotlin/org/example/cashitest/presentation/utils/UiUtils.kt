package org.example.cashitest.presentation.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private object CustomColors {
    val Green = Color(0xFF2E7D32)
}

val MaterialTheme.successColor: Color
    @Composable
    get() = CustomColors.Green