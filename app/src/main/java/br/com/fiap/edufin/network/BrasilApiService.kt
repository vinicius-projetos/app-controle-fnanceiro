package br.com.fiap.edufin.network

import retrofit2.http.GET

interface BrasilApiService {

    @GET("api/taxas/v1")
    suspend fun getTaxas(): List<TaxaResponse>
}
