package br.com.fiap.poupaai.navigation

import androidx.navigation.NavController

sealed class Rota(val caminho: String) {
    data object Painel : Rota("painel")
    data object Orcamento : Rota("orçamento")
    data object CalculadoraDividas : Rota("calculadora_dividas")
    data object Metas : Rota(caminho = "metas")
    data object Historico : Rota(caminho = "histórico")
}

/**
 * Troca de aba sem empilhar telas repetidas e devolvendo a posição da lista
 * quando o usuário volta para uma aba já visitada.
 */
fun NavController.navegarPara(rota: Rota) {
    navigate(route = rota.caminho) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
