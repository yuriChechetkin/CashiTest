package org.example.cashitest

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import org.example.cashitest.di.initKoin
import org.example.cashitest.presentation.transactions_list.TransactionsListScreen

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    Navigator(TransactionsListScreen)
}