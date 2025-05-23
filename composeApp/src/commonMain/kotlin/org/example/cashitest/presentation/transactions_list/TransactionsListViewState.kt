package org.example.cashitest.presentation.transactions_list

import org.example.cashitest.ui_components.models.TransactionView

sealed interface TransactionsListViewState {

    val title: String

    data class Loading(
        override val title: String,
    ) : TransactionsListViewState

    data class Content(
        override val title: String,
        val transactions: List<TransactionView>,
    ) : TransactionsListViewState

    data class EmptyContent(
        override val title: String,
    ) : TransactionsListViewState

    data class Error(
        override val title: String,
        val errorDescription: String,
    ) : TransactionsListViewState
}