package org.example.cashitest.presentation.transactions_list

import app.cash.turbine.test
import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.example.cashitest.createTransactionSample
import org.example.cashitest.domain.repository.TransactionRepository
import org.example.cashitest.presentation.core.Data
import org.example.cashitest.presentation.core.StateMapper
import org.example.cashitest.ui_components.models.TransactionView
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionsListViewModelTest {

    private val repository = mock<TransactionRepository> {
        everySuspend { getTransactions() } calls { listOf(createTransactionSample()) }
    }
    private val mapper =
        mock<StateMapper<TransactionsListContract.DomainState, TransactionsListContract.UiState>> {
            every {
                mapState(
                    domainState = TransactionsListContract.DomainState(transactions = Data(loading = true))
                )
            } calls {
                TransactionsListContract.UiState(
                    viewState = TransactionsListViewState.Loading("title")
                )
            }

            every {
                mapState(
                    domainState =
                        TransactionsListContract.DomainState(
                            transactions = Data(content = listOf(createTransactionSample()))
                        )
                )
            } calls {
                TransactionsListContract.UiState(
                    viewState = TransactionsListViewState.Content(
                        title = "title",
                        transactions = listOf(
                            TransactionView.Item(
                                title = "title",
                                subtitle = "subtitle",
                                value = "value"
                            )
                        )
                    )
                )
            }
        }

    private val dispatcher = UnconfinedTestDispatcher()

    private fun createViewModel() = TransactionsListViewModel(
        stateMapper = mapper,
        repository = repository
    )

    @Test
    fun `WHEN app started THEN updates uiState with loading state returned`() =
        runTest(dispatcher) {
            val viewModel = createViewModel()

            val uiState = TransactionsListContract.UiState(
                viewState = TransactionsListViewState.Loading(title = "title")
            )

            // then
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(uiState, state)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `WHEN loadTransactions THEN updates uiState with fetched transactions`() =
        runTest(dispatcher) {
            val viewModel = createViewModel()

            // when
            viewModel.loadTransactions()

            val uiState = TransactionsListContract.UiState(
                viewState = TransactionsListViewState.Content(
                    title = "title",
                    transactions = listOf(
                        TransactionView.Item(
                            title = "title",
                            subtitle = "subtitle",
                            value = "value"
                        )
                    )
                )
            )

            // then
            viewModel.uiState.test {
                val initialState = awaitItem()
                val contentState = awaitItem()
                assertEquals(uiState, contentState)
                cancelAndIgnoreRemainingEvents()
            }

            verifySuspend { repository.getTransactions() }
        }
}