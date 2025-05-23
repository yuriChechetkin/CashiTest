package org.example.cashitest.presentation.transactions_list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cashitest.domain.repository.TransactionRepository
import org.example.cashitest.presentation.core.Data
import org.example.cashitest.presentation.core.StateMapper
import org.example.cashitest.presentation.transactions_list.TransactionsListContract.DomainState
import org.example.cashitest.presentation.transactions_list.TransactionsListContract.UiState
import org.example.cashitest.presentation.transactions_list.TransactionsListContract.ViewModelApi

internal class TransactionsListViewModel(
    private val stateMapper: StateMapper<DomainState, UiState>,
    private val repository: TransactionRepository
) : ViewModel(), ViewModelApi, StateMapper<DomainState, UiState> by stateMapper {

    @VisibleForTesting
    override val domainState by lazy {
        MutableStateFlow(
            DomainState(
                transactions = Data(loading = true)
            )
        )
    }

    override val uiState: StateFlow<UiState> by lazy {
        domainState
            .map(::mapState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                mapState(domainState.value)
            )
    }

    override fun loadTransactions() {
        viewModelScope.launch {
            fetchTransactions()
        }
    }

    suspend fun fetchTransactions() {
        domainState.update {
            it.copy(transactions = it.transactions.copy(loading = true))
        }
        val transactions = repository.getTransactions()
        domainState.update {
            it.copy(transactions = Data(transactions))
        }
    }
}