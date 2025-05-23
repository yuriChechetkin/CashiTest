package org.example.cashitest.data

interface FirebaseService {

    suspend fun addTransaction(transactionDto: TransactionDto)

    suspend fun fetchTransactions(): List<TransactionDto>
}