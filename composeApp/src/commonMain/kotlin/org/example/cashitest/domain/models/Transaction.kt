package org.example.cashitest.domain.models

data class Transaction(
    val email: String,
    val amount: String,
    val currency: Currency,
    val date: Long,
)

enum class Currency {
    EUR,
    USD,
}