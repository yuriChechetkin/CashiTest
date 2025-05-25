package org.example.cashitest.presentation.transactions_list


import org.example.cashitest.createTransactionSample
import org.example.cashitest.domain.models.Currency
import org.example.cashitest.presentation.core.Data
import org.example.cashitest.ui_components.models.TransactionView
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionsListStateMapperTest {

    private val stateMapper = TransactionsListStateMapper()

    @Test
    fun `WHEN transactions list loaded THEN content state returned`() {
        val transaction1 = createTransactionSample(
            email = "aaa@gmail.com",
            amount = "2000",
            currency = Currency.USD,
        )

        val transaction2 = createTransactionSample(
            email = "bbb@gmail.com",
            amount = "3000",
            currency = Currency.EUR,
        )

        val transactions = listOf(transaction1, transaction2)

        val domainState = TransactionsListContract.DomainState(
            transactions = Data(transactions)
        )

        val uiState = TransactionsListContract.UiState(
            viewState = TransactionsListViewState.Content(
                title = "\uD83D\uDCB0 Transactions • 2",
                transactions = listOf(
                    TransactionView.Header(
                        date = "14 May"
                    ),
                    TransactionView.Item(
                        title = "Sent by aaa@gmail.com",
                        subtitle = "On May 14, 06:40",
                        value = "2000 USD"
                    ),
                    TransactionView.Item(
                        title = "Sent by bbb@gmail.com",
                        subtitle = "On May 14, 06:40",
                        value = "3000 EUR"
                    )
                )
            )
        )

        assertEquals(stateMapper.mapState(domainState), uiState)
    }

    @Test
    fun `WHEN content is loading THEN loading state returned`() {
        val domainState = TransactionsListContract.DomainState(
            transactions = Data(loading = true)
        )

        val uiState = TransactionsListContract.UiState(
            viewState = TransactionsListViewState.Loading(
                title = "\uD83D\uDCB0 Transactions",
            )
        )

        assertEquals(stateMapper.mapState(domainState), uiState)
    }

    @Test
    fun `WHEN error happened THEN error state returned`() {
        val domainState = TransactionsListContract.DomainState(
            transactions = Data(error = Exception("failed to load transactions"))
        )

        val uiState = TransactionsListContract.UiState(
            viewState = TransactionsListViewState.Error(
                title = "\uD83D\uDCB0 Transactions",
                errorDescription = "failed to load transactions error happened, click retry to load content"
            )
        )

        assertEquals(stateMapper.mapState(domainState), uiState)
    }
}