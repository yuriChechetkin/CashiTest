package org.example.cashitest.presentation.core

interface StateMapper<DomainState, UIState> {

    fun mapState(domainState: DomainState): UIState
}