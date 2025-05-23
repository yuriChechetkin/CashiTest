package org.example.cashitest.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual fun Long.toPrettyDateTime(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.dayOfMonth}, ${"%02d".format(dateTime.hour)}:${"%02d".format(dateTime.minute)}"
}