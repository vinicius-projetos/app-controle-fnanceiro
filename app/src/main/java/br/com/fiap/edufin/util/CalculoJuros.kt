package br.com.fiap.edufin.util

import br.com.fiap.edufin.model.Simulacao
import kotlin.math.pow

val PRAZOS_SIMULADOS = listOf(1, 5, 10)

fun simularAportes(aporteMensal: Double, taxaAnual: Double): List<Simulacao> =
    PRAZOS_SIMULADOS.map { anos -> simularAporte(aporteMensal, taxaAnual, anos) }

/**
 * Juros compostos com depósitos iguais no fim de cada mês:
 * montante = aporte * (((1 + i)^n - 1) / i), com i convertido de anual para mensal.
 */
fun simularAporte(aporteMensal: Double, taxaAnual: Double, anos: Int): Simulacao {
    val meses = anos * 12
    val taxaMensal = (1 + taxaAnual / 100).pow(1.0 / 12) - 1
    val investido = aporteMensal * meses
    val montante = if (taxaMensal <= 0.0) {
        investido
    } else {
        aporteMensal * (((1 + taxaMensal).pow(meses) - 1) / taxaMensal)
    }

    return Simulacao(
        anos = anos,
        totalInvestido = investido,
        totalFinal = montante
    )
}
