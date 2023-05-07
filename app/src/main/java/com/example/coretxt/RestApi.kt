package com.example.coretxt

import android.content.Context
import android.graphics.Bitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import java.io.ByteArrayOutputStream


class RestApi(val context: Context) {
    companion object {
        private const val HOST = "http://coretxt.ddns.net:8000/"
        private val client = HttpClient()

        public fun sendImage(bmp: Bitmap): String {
            var body: String

            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            runBlocking {
                val response: HttpResponse = client.submitFormWithBinaryData(
                    url = HOST + "image",
                    formData = formData {
                        append("image", byteArray, Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"image.png\"")
                        })
                    }
                )
                body = response.body()
            }
            client.close()

            return body
        }

        public fun sendText(text: String): String {
            var body: String = "Error"

            runBlocking {
                 val response: HttpResponse = client.submitForm(
                     url = HOST + "text",
                     formParameters = Parameters.build {
                         append("text", text)
                     })
                body = response.body()
            }
            client.close()

            return body
        }
    }
}