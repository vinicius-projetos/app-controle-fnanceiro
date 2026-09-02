package br.com.fiap.edufin.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import br.com.fiap.edufin.model.Gasto
import br.com.fiap.edufin.model.ResumoMensal

/**
 * Guarda o orçamento em memória para que o painel e a tela de orçamento mostrem
 * sempre o mesmo número. Entra DataStore no lugar quando chegar a persistência.
 */
object OrcamentoRepository {

    var renda by mutableDoubleStateOf(2400.00)
        private set

    private val listaGastos = mutableStateListOf(
        Gasto(id = 1, nome = "Aluguel", valor = 950.00),
        Gasto(id = 2, nome = "Mercado", valor = 620.50),
        Gasto(id = 3, nome = "Transporte", valor = 180.00),
        Gasto(id = 4, nome = "Celular", valor = 120.00)
    )

    val gastos: List<Gasto>
        get() = listaGastos

    val totalGastos: Double
        get() = listaGastos.sumOf { it.valor }

    val resumo: ResumoMensal
        get() = ResumoMensal(renda = renda, gastos = totalGastos)

    fun atualizarRenda(valor: Double) {
        renda = valor
    }

    fun adicionarGasto(nome: String, valor: Double) {
        val proximoId = (listaGastos.maxOfOrNull { it.id } ?: 0) + 1
        listaGastos.add(Gasto(id = proximoId, nome = nome, valor = valor))
    }

    fun removerGasto(gasto: Gasto) {
        listaGastos.remove(gasto)
    }
}
