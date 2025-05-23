package org.example.cashitest.presentation.transactions_list

import org.example.cashitest.domain.models.Transaction
import org.example.cashitest.presentation.core.BaseViewModel
import org.example.cashitest.presentation.core.Data

internal interface TransactionsListContract {

    interface ViewModelApi : BaseViewModel<DomainState, UiState> {

        fun loadTransactions()
    }

    data class DomainState(
        val transactions: Data<List<Transaction>>
    ) : BaseViewModel.DomainState

    data class UiState(
        val viewState: TransactionsListViewState
    ) : BaseViewModel.UiState

}