package org.example.cashitest.ui_components.models

sealed interface TransactionView {
    data class Item(
        val title: String,
        val subtitle: String,
        val value: String,
    ) : TransactionView

    data class Header(
        val date: String
    ) : TransactionView
}