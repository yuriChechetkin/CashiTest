package org.example.cashitest.backend

import org.example.cashitest.data.TransactionDto

interface NetworkService {
    suspend fun sendTransfer(transaction: TransactionDto): Boolean
}