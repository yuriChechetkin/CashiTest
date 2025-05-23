package org.example.cashitest.data

import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirebaseServiceImpl(
    private val firestore: FirebaseFirestore
): FirebaseService {

    override suspend fun addTransaction(transactionDto: TransactionDto) {
        firestore
            .collection("TRANSACTIONS")
            .add(transactionDto)
    }

    override suspend fun fetchTransactions(): List<TransactionDto> {
        return try {
            val transactionsResponse = firestore.collection("TRANSACTIONS").get()
            return transactionsResponse.documents.map {
                it.data<TransactionDto>()
            }
        } catch (exception: Exception) {
            throw exception
        }
    }
}