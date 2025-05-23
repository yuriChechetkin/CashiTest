package org.example.cashitest.presentation.transactions_list

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDate
import org.example.cashitest.domain.models.Transaction
import org.example.cashitest.presentation.core.Data
import org.example.cashitest.presentation.core.StateMapper
import org.example.cashitest.presentation.transactions_list.TransactionsListContract.DomainState
import org.example.cashitest.presentation.transactions_list.TransactionsListContract.UiState
import org.example.cashitest.presentation.utils.toPrettyDate
import org.example.cashitest.presentation.utils.toPrettyDateTime
import org.example.cashitest.ui_components.models.TransactionView

internal class TransactionsListStateMapper : StateMapper<DomainState, UiState> {

    override fun mapState(domainState: DomainState): UiState {
        return domainState.transactions.mapDataState().run { UiState(this) }
    }

    private fun Data<List<Transaction>>.mapDataState(): TransactionsListViewState {
        return when {
            content != null -> mapContentState(content)
            loading -> TransactionsListViewState.Loading(title = mapTitle())
            error != null -> mapErrorState(error)
            else -> throw IllegalStateException("no content, no loading, no error state")
        }
    }

    private fun mapContentState(transactions: List<Transaction>): TransactionsListViewState {
        return when (transactions.isEmpty()) {
            true -> TransactionsListViewState.EmptyContent(title = mapTitle())
            else -> TransactionsListViewState.Content(
                title = mapTitle(transactions),
                transactions = transactions.toTransactionViews()
            )
        }
    }

    private fun mapErrorState(exception: Exception): TransactionsListViewState.Error {
        return TransactionsListViewState.Error(
            title = mapTitle(),
            errorDescription = exception.message?.let {
                "$it error happened, click retry to load content"
            } ?: "Something went wrong, click retry to load content"
        )
    }

    private fun List<Transaction>.toTransactionViews(
        zone: TimeZone = TimeZone.currentSystemDefault()
    ): List<TransactionView> {
        val sorted = this.sortedByDescending { it.date }

        val grouped: Map<LocalDate, List<Transaction>> = sorted.groupBy { tx ->
            Instant.fromEpochMilliseconds(tx.date)
                .toLocalDateTime(zone)
                .date
        }

        return grouped.entries
            .toList()
            .sortedByDescending { it.key }
            .flatMap { (day, txs) ->
                val header = TransactionView.Header(date = day.toPrettyDate())
                val items = txs.map { transaction ->
                    TransactionView.Item(
                        title = "Sent by ${transaction.email}",
                        subtitle = "On ${transaction.date.toPrettyDateTime()}",
                        value = "${transaction.amount} ${transaction.currency}"
                    )
                }
                listOf<TransactionView>(header) + items
            }
    }

    private fun mapTitle(transactions: List<Transaction>? = null): String {
        return transactions?.size?.takeIf { it > 0 }?.let {
            "\uD83D\uDCB0 Transactions • $it"
        } ?: "\uD83D\uDCB0 Transactions"
    }
}