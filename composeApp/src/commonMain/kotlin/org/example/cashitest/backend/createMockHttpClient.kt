package org.example.cashitest.backend

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.http.content.TextContent
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PaymentRequest(
    val recipientEmail: String,
    val amount: Double,
    val currency: String,
    val time: Long
)

@Serializable
data class ErrorResponse(val error: String)

fun createMockHttpClient(): HttpClient = HttpClient(MockEngine) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    engine {
        addHandler { request ->
            if (request.url.encodedPath == "/payments" && request.method == HttpMethod.Post) {
                val text = (request.body as? TextContent)?.text
                    ?: return@addHandler respondError(HttpStatusCode.BadRequest)

                val payload = Json.decodeFromString(PaymentRequest.serializer(), text)

                val error = when {
                    !payload.recipientEmail.contains("@") -> "Invalid email format"
                    payload.amount <= 0.0 -> "Amount must be > 0"
                    payload.currency.length != 3 -> "Currency must be 3 letters"
                    else -> null
                }

                delay(300)

                return@addHandler if (error != null) {
                    respond(
                        content = Json.encodeToString(ErrorResponse(error)),
                        status = HttpStatusCode.BadRequest,
                        headers = headersOf("Content-Type", "application/json")
                    )
                } else {
                    respond(
                        content = """{"result":"ok"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type", "application/json")
                    )
                }
            }

            respondError(HttpStatusCode.NotFound)
        }
    }
}
