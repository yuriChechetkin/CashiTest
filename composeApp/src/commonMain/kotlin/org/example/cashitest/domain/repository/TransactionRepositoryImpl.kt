package org.example.cashitest.domain.repository

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.datetime.Clock
import org.example.cashitest.backend.NetworkService
import org.example.cashitest.data.FirebaseService
import org.example.cashitest.data.TransactionDto
import org.example.cashitest.domain.models.Currency
import org.example.cashitest.domain.models.Transaction
import org.example.cashitest.domain.mappers.mapToDomain

class TransactionRepositoryImpl(
    private val firebaseService: FirebaseService,
    private val networkService: NetworkService,
) : TransactionRepository {

    override suspend fun sendPayment(
        email: String,
        amount: String,
        currency: Currency
    ) {
        val nowTime = Clock.System.now()
        val date = Timestamp(
            seconds = nowTime.epochSeconds,
            nanoseconds = nowTime.nanosecondsOfSecond
        )


        val transaction = TransactionDto(
            email = email,
            amount = amount,
            currency = currency.name,
            date = date
        )

        val successfullySent = networkService.sendTransfer(transaction)

        if (successfullySent) {
            firebaseService.addTransaction(transaction)
        }
    }

    override suspend fun getTransactions(): List<Transaction> {
        try {
            return firebaseService.fetchTransactions().map { it.mapToDomain() }
        } catch (e: Exception) {
            throw e
        }
    }
}