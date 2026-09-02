package br.com.fiap.edufin.model

data class ResumoMensal(
    val renda: Double = 0.0,
    val gastos: Double = 0.0
) {
    val sobra: Double
        get() = renda - gastos

    /**
     * A folga é o percentual da renda que sobra no fim do mês. A régua segue a
     * recomendação de reservar ao menos 20% da renda, usada em educação financeira.
     */
    val nivel: NivelSaude
        get() {
            if (renda <= 0.0) return NivelSaude.CRITICA
            val folga = sobra / renda
            return when {
                folga >= 0.2 -> NivelSaude.BOA
                folga > 0.0 -> NivelSaude.ATENCAO
                else -> NivelSaude.CRITICA
            }
        }
}
