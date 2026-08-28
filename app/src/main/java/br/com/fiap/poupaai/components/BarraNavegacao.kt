package br.com.fiap.poupaai.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.poupaai.R
import br.com.fiap.poupaai.navigation.Rota

@Composable
fun BarraNavegacao(
    rotaAtual: String,
    aoNavegar: (Rota) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {

        ItemNavegacao(
            icone = Icons.Default.Home,
            rotulo = stringResource(id = R.string.nav_dashboard),
            selecionado = rotaAtual == Rota.Painel.caminho,
            aoClicar = {
                aoNavegar(Rota.Painel)
            }
        )

        ItemNavegacao(
            icone = Icons.Default.AccountBalanceWallet,
            rotulo = stringResource(id = R.string.nav_budget),
            selecionado = rotaAtual == Rota.Orcamento.caminho,
            aoClicar = {
                aoNavegar(Rota.Orcamento)
            }
        )

        ItemNavegacao(
            icone = Icons.AutoMirrored.Filled.TrendingUp,
            rotulo = stringResource(id = R.string.nav_simulators),
            selecionado = rotaAtual == Rota.CalculadoraDividas.caminho,
            aoClicar = {
                aoNavegar(Rota.CalculadoraDividas)
            }
        )

        // Metas ganhará uma rota quando a tela for construída.
        ItemNavegacao(
            icone = Icons.Default.Flag,
            rotulo = stringResource(id = R.string.nav_goals),
            habilitado = false
        )
    }
}

@Composable
private fun RowScope.ItemNavegacao(
    icone: ImageVector,
    rotulo: String,
    selecionado: Boolean = false,
    habilitado: Boolean = true,
    aoClicar: () -> Unit = { }
) {
    NavigationBarItem(
        selected = selecionado,
        enabled = habilitado,
        onClick = aoClicar,
        icon = {
            Icon(
                imageVector = icone,
                contentDescription = rotulo
            )
        },
        label = {
            Text(
                text = rotulo,
                style = MaterialTheme.typography.bodySmall
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
            unselectedIconColor = MaterialTheme.colorScheme.secondary,
            unselectedTextColor = MaterialTheme.colorScheme.secondary
        )
    )
}