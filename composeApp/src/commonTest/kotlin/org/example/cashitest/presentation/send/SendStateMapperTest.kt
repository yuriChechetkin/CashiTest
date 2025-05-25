package org.example.cashitest.presentation.send

import kotlin.test.Test
import kotlin.test.assertEquals

class SendStateMapperTest {

    private val stateMapper = SendStateMapper()

    @Test
    fun `WHEN email and amount is empty THEN empty state returned`() {
        val domainState = SendContract.DomainState(
            email = "",
            amount = ""
        )

        val uiState = SendContract.UiState(SendViewState.Empty)

        assertEquals(stateMapper.mapState(domainState), uiState)
    }

    @Test
    fun `WHEN email is not correct THEN disabled state returned`() {
        val domainState = SendContract.DomainState(
            email = "not_correct_email",
            amount = ""
        )

        val uiState = SendContract.UiState(SendViewState.Disabled)

        assertEquals(stateMapper.mapState(domainState), uiState)
    }

    @Test
    fun `WHEN domain is loading THEN loading state returned`() {
        val domainState = SendContract.DomainState(
            email = "",
            amount = "",
            loading = true
        )

        val uiState = SendContract.UiState(SendViewState.Loading)

        assertEquals(stateMapper.mapState(domainState), uiState)
    }

    @Test
    fun `WHEN payment sent THEN success state returned`() {
        val domainState = SendContract.DomainState(
            email = "email@gmail.com",
            amount = "2000",
            paymentSent = true
        )

        val uiState = SendContract.UiState(SendViewState.Successful)

        assertEquals(stateMapper.mapState(domainState), uiState)
    }

    @Test
    fun `WHEN correct data entered THEN correct state returned`() {
        val domainState = SendContract.DomainState(
            email = "email@gmail.com",
            amount = "2000",
        )

        val uiState = SendContract.UiState(SendViewState.Correct)

        assertEquals(stateMapper.mapState(domainState), uiState)
    }
}