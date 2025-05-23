package org.example.cashitest.presentation.core

data class Data<T>(
    val content: T? = null,
    val loading: Boolean = false,
    val error: Exception? = null
)