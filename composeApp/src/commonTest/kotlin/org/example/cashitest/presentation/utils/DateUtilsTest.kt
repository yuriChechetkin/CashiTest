package org.example.cashitest.presentation.utils

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilsTest {

    @Test
    fun `WHEN map local date to pretty THEN correct text returned`() = runTest {
        val localDate = LocalDate(2025, 5, 15)

        val expectedDate = "15 May"

        assertEquals(localDate.toPrettyDate(), expectedDate)
    }
}