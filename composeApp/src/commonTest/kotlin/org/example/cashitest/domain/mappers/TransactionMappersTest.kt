package org.example.cashitest.domain.mappers

import kotlinx.coroutines.test.runTest
import org.example.cashitest.createTransactionDtoSample
import org.example.cashitest.createTransactionSample
import org.example.cashitest.domain.models.Currency
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionMappersTest {

    @Test
    fun `WHEN map dto to domain transaction model THEN correct model returned`() = runTest {
        val transactionDto = createTransactionDtoSample(
            email = "test@test.com",
            amount = "999",
            currency = "EUR"
        )

        val transactionDomain = createTransactionSample(
            email = "test@test.com",
            amount = "999",
            currency = Currency.EUR
        )

        assertEquals(transactionDto.mapToDomain(), transactionDomain)
    }
}