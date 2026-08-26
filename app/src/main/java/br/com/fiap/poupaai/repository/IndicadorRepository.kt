package br.com.fiap.poupaai.repository

import br.com.fiap.poupaai.model.Indicador
import java.time.LocalDate

/**
 * Valores fixos por enquanto. Esta função será trocada pela consulta à BrasilAPI
 * sem que as telas precisem ser alteradas.
 */
fun getAllIndicadores() = listOf(
    Indicador(
        id = 1,
        sigla = "SELIC",
        nome = "Taxa básica de juros",
        valor = 15.00,
        atualizadoEm = LocalDate.now()
    ),
    Indicador(
        id = 2,
        sigla = "CDI",
        nome = "Certificado de Depósito Interbancário",
        valor = 14.90,
        atualizadoEm = LocalDate.now()
    ),
    Indicador(
        id = 3,
        sigla = "IPCA",
        nome = "Inflação oficial",
        valor = 4.62,
        atualizadoEm = LocalDate.now()
    ),
    Indicador(
        id = 4,
        sigla = "POUPANÇA",
        nome = "Rendimento da caderneta",
        valor = 6.17,
        atualizadoEm = LocalDate.now()
    )
)
