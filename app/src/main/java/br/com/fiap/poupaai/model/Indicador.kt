package br.com.fiap.poupaai.model

import java.time.LocalDate

data class Indicador(
    val id: Int = 0,
    val sigla: String = "",
    val nome: String = "",
    val valor: Double = 0.0,
    val atualizadoEm: LocalDate = LocalDate.now()
)
