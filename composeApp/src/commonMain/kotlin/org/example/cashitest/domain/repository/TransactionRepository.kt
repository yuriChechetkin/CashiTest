package org.example.cashitest.domain.repository

import org.example.cashitest.domain.models.Currency
import org.example.cashitest.domain.models.Transaction

interface TransactionRepository {
    suspend fun sendPayment(
        email: String,
        amount: String,
        currency: Currency
    )
    suspend fun getTransactions(): List<Transaction>
}