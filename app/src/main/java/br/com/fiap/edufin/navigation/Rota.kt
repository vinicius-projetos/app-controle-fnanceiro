package br.com.fiap.edufin.navigation

sealed class Rota(val caminho: String) {
    data object Painel : Rota("painel")
    data object Orcamento : Rota("orcamento")
    data object CalculadoraDividas : Rota("calculadora_dividas")
    data object SimuladorInvestimentos : Rota("simulador_investimentos")
    data object Metas : Rota("metas")
    data object Historico : Rota("historico")
}
