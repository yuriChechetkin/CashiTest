package org.example.cashitest.presentation.send

sealed interface SendViewState {

    data object Empty : SendViewState

    data object Correct : SendViewState

    data object Disabled : SendViewState

    data object Loading : SendViewState

    data object Successful : SendViewState

    fun buttonEnabled() = this == Correct
}