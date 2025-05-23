package org.example.cashitest.presentation.send

import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.example.cashitest.domain.models.Currency
import org.example.cashitest.domain.repository.TransactionRepository
import org.example.cashitest.presentation.core.StateMapper
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SendViewModelTest {

    private val repository = mock<TransactionRepository> {
        everySuspend { sendPayment(any(), any(), any()) } calls { }
    }

    private val mapper = mock<StateMapper<SendContract.DomainState, SendContract.UiState>> {
        every { mapState(any()) } calls { SendContract.UiState(SendViewState.Correct) }
    }

    private val dispatcher = UnconfinedTestDispatcher()

    private fun createViewModel() = SendViewModel(
        stateMapper = mapper,
        repository = repository
    )

    @Test
    fun `WHEN data entered and button clicked THEN invoked repository with correct params`() =
        runTest(dispatcher) {
            val viewModel = createViewModel()

            // when
            viewModel.inputEmail("email@mail.com")
            viewModel.inputAmount("3000")
            viewModel.selectCurrency(Currency.EUR)
            viewModel.onSendButtonClicked()

            // then
            verifySuspend {
                repository.sendPayment("email@mail.com", "3000", Currency.EUR)
            }
        }

}