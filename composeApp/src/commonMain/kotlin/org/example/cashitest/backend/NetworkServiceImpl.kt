package org.example.cashitest.backend

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.example.cashitest.data.TransactionDto

private const val BASE_URL = "https://api.cashitest.com"

class NetworkServiceImpl(
    private val client: HttpClient,
) : NetworkService {

    override suspend fun sendTransfer(transaction: TransactionDto): Boolean {
        val resp = client.post("$BASE_URL/payments") {
            contentType(ContentType.Application.Json)
            val body = PaymentRequest(
                recipientEmail = transaction.email,
                amount = transaction.amount.toDouble(),
                currency = transaction.currency,
                time = transaction.date.seconds * 1000
            )
            setBody(body)
        }
        return resp.status == HttpStatusCode.OK
    }
}