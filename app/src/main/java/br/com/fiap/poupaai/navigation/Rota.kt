package br.com.fiap.poupaai.navigation

import androidx.navigation.NavController

sealed class Rota(val caminho: String) {
    data object Painel : Rota("painel")
    data object Orcamento : Rota("orcamento")
}

/**
 * Troca de aba sem empilhar telas repetidas e devolvendo a posição da lista
 * quando o usuário volta para uma aba já visitada.
 */
fun NavController.navegarPara(rota: Rota) {
    navigate(rota.caminho) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
