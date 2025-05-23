package org.example.cashitest.di

import org.example.cashitest.presentation.core.StateMapper
import org.example.cashitest.presentation.send.SendContract
import org.example.cashitest.presentation.send.SendStateMapper
import org.example.cashitest.presentation.send.SendViewModel
import org.example.cashitest.presentation.transactions_list.TransactionsListContract
import org.example.cashitest.presentation.transactions_list.TransactionsListStateMapper
import org.example.cashitest.presentation.transactions_list.TransactionsListViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val transactionsFeatureModule = module {
    single(named("TransactionsMapper")) {
        TransactionsListStateMapper() as StateMapper<TransactionsListContract.DomainState, TransactionsListContract.UiState>
    }
    viewModel {
        TransactionsListViewModel(get(named("TransactionsMapper")), get())
    }
}

val sendFeatureModule = module {
    single(named("SendMapper")) {
        SendStateMapper() as StateMapper<SendContract.DomainState, SendContract.UiState>
    }
    viewModel {
        SendViewModel(get(named("SendMapper")), get())
    }
}