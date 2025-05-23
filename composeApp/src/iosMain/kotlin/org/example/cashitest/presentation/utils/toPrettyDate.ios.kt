package org.example.cashitest.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import platform.Foundation.*
import platform.Foundation.NSDateFormatter

actual fun Long.toPrettyDateTime(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val formatter = NSDateFormatter()
    formatter.dateFormat = "MMM dd, HH:mm"
    formatter.locale = NSLocale.localeWithLocaleIdentifier("en_US")

    val dateComponents = NSDateComponents().apply {
        year = local.year.toLong()
        month = local.monthNumber.toLong()
        day = local.dayOfMonth.toLong()
        hour = local.hour.toLong()
        minute = local.minute.toLong()
    }

    val calendar = NSCalendar.currentCalendar
    val nsDate = calendar.dateFromComponents(dateComponents) ?: NSDate()
    return formatter.stringFromDate(nsDate)
}