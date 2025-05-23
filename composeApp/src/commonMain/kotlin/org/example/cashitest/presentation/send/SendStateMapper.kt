package org.example.cashitest.presentation.send

import org.example.cashitest.presentation.core.StateMapper
import org.example.cashitest.presentation.send.SendContract.DomainState
import org.example.cashitest.presentation.send.SendContract.UiState
import org.example.cashitest.ui_components.views.isValidEmail

internal class SendStateMapper : StateMapper<DomainState, UiState> {

    override fun mapState(domainState: DomainState): UiState {
        return UiState(validateState(domainState))
    }

    private fun validateState(domainState: DomainState): SendViewState {
        return when {
            domainState.loading -> SendViewState.Loading
            domainState.email.isEmpty() && domainState.amount.isEmpty() -> SendViewState.Empty
            domainState.email.isValidEmail().not() || domainState.amount.isEmpty() -> SendViewState.Disabled
            domainState.amount.toLong() == 0L -> SendViewState.Disabled
            domainState.paymentSent -> SendViewState.Successful
            else -> SendViewState.Correct
        }
    }
}