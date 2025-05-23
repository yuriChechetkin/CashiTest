package org.example.cashitest.ui_components.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AmountInput(
    amount: String,
    onAmountChange: (String) -> Unit
) {
    OutlinedTextField(
        value = amount,
        onValueChange = { input ->
            if (input.all { it.isDigit() }) {
                onAmountChange(input.toIntOrNull()?.toString() ?: "")
            }
        },
        label = { Text("Amount") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}