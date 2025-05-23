package org.example.cashitest.presentation.utils

import kotlinx.datetime.LocalDate

expect fun Long.toPrettyDateTime(): String

fun LocalDate.toPrettyDate(): String {
    return "${this.dayOfMonth} ${this.month.name.lowercase().replaceFirstChar { it.uppercase() }}"
}