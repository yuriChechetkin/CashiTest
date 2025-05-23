package org.example.cashitest.data

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    val email: String,
    val amount: String,
    val currency: String,
    @Contextual
    val date: Timestamp,
)