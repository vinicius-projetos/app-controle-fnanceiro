package br.com.fiap.edufin.util

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Os valores do app são sempre em real, por isso o locale é fixo em pt-BR. */
private val localeBrasil: Locale = Locale.forLanguageTag("pt-BR")

fun formatarMoeda(valor: Double): String =
    NumberFormat.getCurrencyInstance(localeBrasil).format(valor)

fun formatarValor(valor: Double): String =
    String.format(localeBrasil, "%.2f", valor)

fun formatarPercentual(valor: Double): String =
    String.format(localeBrasil, "%.2f%%", valor)

fun formatarData(data: LocalDate): String =
    data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", localeBrasil))

/**
 * Aceita tanto "1.234,56" quanto "1234.56", já que o teclado numérico do Android
 * oferece os dois separadores dependendo do aparelho.
 */
fun paraDouble(texto: String): Double? {
    val limpo = texto.trim()
    if (limpo.isEmpty()) return null

    val normalizado = if (limpo.contains(",")) {
        limpo.replace(".", "").replace(",", ".")
    } else {
        limpo
    }

    return normalizado.toDoubleOrNull()
}
