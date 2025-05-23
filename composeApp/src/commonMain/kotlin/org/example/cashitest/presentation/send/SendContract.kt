package org.example.cashitest.presentation.send

import org.example.cashitest.domain.models.Currency
import org.example.cashitest.presentation.core.BaseViewModel

interface SendContract {

    interface ViewModelApi : BaseViewModel<DomainState, UiState> {

        fun inputEmail(email: String)

        fun inputAmount(amount: String)

        fun selectCurrency(currency: Currency)

        fun onSendButtonClicked()
    }

    data class DomainState(
        val email: String,
        val amount: String,
        val selectedCurrency: Currency = Currency.USD,
        val paymentSent: Boolean = false,
        val loading: Boolean = false,
        val error: Exception? = null,
    ) : BaseViewModel.DomainState

    data class UiState(
        val viewState: SendViewState
    ) : BaseViewModel.UiState
}