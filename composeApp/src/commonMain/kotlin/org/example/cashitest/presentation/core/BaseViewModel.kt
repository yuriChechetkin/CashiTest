package org.example.cashitest.presentation.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface BaseViewModel<A: BaseViewModel.DomainState, B: BaseViewModel.UiState> {

    val domainState: MutableStateFlow<A>

    val uiState: StateFlow<B>

    interface DomainState

    interface UiState
}