package org.example.cashitest.presentation.send

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
import org.example.cashitest.domain.models.Currency
import org.example.cashitest.domain.repository.TransactionRepository
import org.example.cashitest.presentation.core.StateMapper
import org.example.cashitest.presentation.send.SendContract.DomainState
import org.example.cashitest.presentation.send.SendContract.UiState
import org.example.cashitest.presentation.send.SendContract.ViewModelApi

class SendViewModel internal constructor(
    private val stateMapper: StateMapper<DomainState, UiState>,
    private val repository: TransactionRepository
) : ViewModel(), ViewModelApi, StateMapper<DomainState, UiState> by stateMapper {

    @VisibleForTesting
    override val domainState by lazy {
        MutableStateFlow(
            DomainState(
                email = "",
                amount = "",
                selectedCurrency = Currency.USD,
                paymentSent = false,
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

    override fun inputEmail(email: String) {
        domainState.update { it.copy(email = email) }
    }

    override fun inputAmount(amount: String) {
        domainState.update { it.copy(amount = amount) }
    }

    override fun selectCurrency(currency: Currency) {
        domainState.update { it.copy(selectedCurrency = currency) }
    }

    override fun onSendButtonClicked() {
        viewModelScope.launch {
            sendPayment()
        }
    }

    private suspend fun sendPayment() {
        domainState.value.let {
            domainState.update { it.copy(loading = true) }
            try {
                repository.sendPayment(
                    email = it.email,
                    amount = it.amount,
                    currency = it.selectedCurrency,
                )
                domainState.update { it.copy(paymentSent = true) }
            } catch (exception: Exception) {
                domainState.update {
                    it.copy(error = exception)
                }
            } finally {
                domainState.update { it.copy(loading = false) }
            }
        }
    }
}