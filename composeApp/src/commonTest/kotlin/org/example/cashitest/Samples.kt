package org.example.cashitest

import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.fromMilliseconds
import org.example.cashitest.data.TransactionDto
import org.example.cashitest.domain.models.Currency
import org.example.cashitest.domain.models.Transaction

fun createTransactionDtoSample(
    email: String = "email@gmail.com",
    amount: String = "3000",
    currency: String = "USD",
    date: Double = 1747190400000.0
) = TransactionDto(
    email = email,
    amount = amount,
    currency = currency,
    date = Timestamp.fromMilliseconds(date)
)

fun createTransactionSample(
    email: String = "email@gmail.com",
    amount: String = "3000",
    currency: Currency = Currency.USD,
    date: Long = 1747190400000
) = Transaction(
    email = email,
    amount = amount,
    currency = currency,
    date = date
)