package org.example.cashitest.domain.mappers

import org.example.cashitest.data.TransactionDto
import org.example.cashitest.domain.models.Currency
import org.example.cashitest.domain.models.Transaction

fun TransactionDto.mapToDomain(): Transaction {
    return Transaction(
        email = this.email,
        amount = this.amount,
        currency = Currency.valueOf(this.currency),
        date = this.date.seconds.secondsToMillis()
    )
}

private fun Long.secondsToMillis() = this * 1000