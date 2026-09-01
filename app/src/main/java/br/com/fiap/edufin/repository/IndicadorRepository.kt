package br.com.fiap.edufin.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.fiap.edufin.model.Indicador
import br.com.fiap.edufin.network.TaxaResponse
import br.com.fiap.edufin.network.criarBrasilApi
import java.time.LocalDate
import kotlin.math.pow

/**
 * Busca SELIC, CDI e IPCA em https://brasilapi.com.br/api/taxas/v1.
 * A Poupança não vem nesse endpoint, então é calculada pela regra do Banco Central.
 * Se a rede falhar, o app continua com a última lista conhecida.
 */
object IndicadorRepository {

    private val api = criarBrasilApi()

    var indicadores by mutableStateOf(indicadoresLocais())
        private set

    var veioDaApi by mutableStateOf(false)
        private set

    suspend fun atualizar() {
        try {
            val taxas = api.getTaxas()
            indicadores = mapearTaxas(taxas)
            veioDaApi = true
        } catch (_: Exception) {
            veioDaApi = false
        }
    }
}

fun getAllIndicadores(): List<Indicador> = IndicadorRepository.indicadores

private fun indicadoresLocais() = listOf(
    Indicador(1, "SELIC", "Taxa básica de juros", 15.00),
    Indicador(2, "CDI", "Certificado de Depósito Interbancário", 14.90),
    Indicador(3, "IPCA", "Inflação oficial", 4.62),
    Indicador(4, "POUPANÇA", "Rendimento da caderneta", 6.17)
)

private fun mapearTaxas(taxas: List<TaxaResponse>): List<Indicador> {
    val hoje = LocalDate.now()
    val selic = taxas.valorDe("Selic")
    val cdi = taxas.valorDe("CDI")
    val ipca = taxas.valorDe("IPCA")

    return listOf(
        Indicador(1, "SELIC", "Taxa básica de juros", selic ?: 15.00, hoje),
        Indicador(2, "CDI", "Certificado de Depósito Interbancário", cdi ?: 14.90, hoje),
        Indicador(3, "IPCA", "Inflação oficial", ipca ?: 4.62, hoje),
        Indicador(
            id = 4,
            sigla = "POUPANÇA",
            nome = "Rendimento da caderneta",
            valor = rendimentoPoupancaAnual(selic ?: 15.00),
            atualizadoEm = hoje
        )
    )
}

private fun List<TaxaResponse>.valorDe(nome: String): Double? =
    firstOrNull { it.nome.equals(nome, ignoreCase = true) }?.valor

/**
 * Com SELIC acima de 8,5% ao ano, a caderneta rende 0,5% ao mês.
 * Abaixo disso, rende 70% da SELIC.
 */
private fun rendimentoPoupancaAnual(selic: Double): Double =
    if (selic > 8.5) ((1.005).pow(12) - 1) * 100 else selic * 0.7
