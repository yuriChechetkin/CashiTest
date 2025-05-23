package org.example.cashitest.domain.repository

import dev.mokkery.answering.calls
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import org.example.cashitest.backend.NetworkService
import org.example.cashitest.createTransactionDtoSample
import org.example.cashitest.createTransactionSample
import org.example.cashitest.data.FirebaseService
import org.example.cashitest.domain.models.Currency
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionRepositoryImplTest {

    val networkService = mock<NetworkService> {
        everySuspend { sendTransfer(any()) } calls { true }
    }

    val firebaseService = mock<FirebaseService> {
        everySuspend { addTransaction(any()) } calls { }
        everySuspend { fetchTransactions() } calls { listOf(createTransactionDtoSample()) }
    }

    private lateinit var repository: TransactionRepositoryImpl

    @BeforeTest
    fun setup() {
        repository = TransactionRepositoryImpl(
            networkService = networkService,
            firebaseService = firebaseService
        )
    }

    @Test
    fun `WHEN send payment requested THEN network service sendTransfer and addTransaction invoked`() =
        runTest {
            repository.sendPayment("email@email.com", "1000", Currency.USD)

            verifySuspend {
                networkService.sendTransfer(any())
                firebaseService.addTransaction(any())
            }
        }

    @Test
    fun `WHEN get transactions requested THEN firebase service invoked and correct data returned`() =
        runTest {
            val transactions = repository.getTransactions()

            verifySuspend {
                firebaseService.fetchTransactions()
            }

            val expected = listOf(createTransactionSample())

            assertEquals(transactions, expected)
        }
}