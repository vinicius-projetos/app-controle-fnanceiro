package br.com.fiap.edufin.model

data class Simulacao(
    val anos: Int = 0,
    val totalInvestido: Double = 0.0,
    val totalFinal: Double = 0.0
) {
    val juros: Double
        get() = totalFinal - totalInvestido

    /** Quanto do montante final saiu do bolso, em vez de ter vindo dos juros. */
    val proporcaoInvestida: Float
        get() = if (totalFinal <= 0.0) 0f
        else (totalInvestido / totalFinal).toFloat().coerceIn(0f, 1f)
}
