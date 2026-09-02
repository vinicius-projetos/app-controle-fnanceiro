package br.com.fiap.edufin.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val URL_BRASIL_API = "https://brasilapi.com.br/"

fun criarBrasilApi(): BrasilApiService {
    val cliente = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder()
        .baseUrl(URL_BRASIL_API)
        .client(cliente)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BrasilApiService::class.java)
}
