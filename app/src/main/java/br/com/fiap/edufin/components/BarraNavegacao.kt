package br.com.fiap.edufin.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.edufin.R
import br.com.fiap.edufin.navigation.Rota

@Composable
fun BarraNavegacao(
    rotaAtual: String,
    aoNavegar: (Rota) -> Unit
) {
    val cores = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
        unselectedIconColor = MaterialTheme.colorScheme.secondary,
        unselectedTextColor = MaterialTheme.colorScheme.secondary
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {

        NavigationBarItem(
            selected = rotaAtual == Rota.Painel.caminho,
            onClick = { aoNavegar(Rota.Painel) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = R.string.nav_dashboard)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nav_dashboard),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = cores
        )

        NavigationBarItem(
            selected = rotaAtual == Rota.Orcamento.caminho,
            onClick = { aoNavegar(Rota.Orcamento) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = stringResource(id = R.string.nav_budget)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nav_budget),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = cores
        )

        NavigationBarItem(
            selected = rotaAtual == Rota.CalculadoraDividas.caminho,
            onClick = { aoNavegar(Rota.CalculadoraDividas) },
            icon = {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = stringResource(id = R.string.nav_debts)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nav_debts),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = cores
        )

        NavigationBarItem(
            selected = rotaAtual == Rota.SimuladorInvestimentos.caminho,
            onClick = { aoNavegar(Rota.SimuladorInvestimentos) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                    contentDescription = stringResource(id = R.string.nav_invest)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nav_invest),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = cores
        )

        // Metas ganhará uma rota quando a tela for construída.
        NavigationBarItem(
            selected = false,
            enabled = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = stringResource(id = R.string.nav_goals)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nav_goals),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = cores
        )
    }
}
