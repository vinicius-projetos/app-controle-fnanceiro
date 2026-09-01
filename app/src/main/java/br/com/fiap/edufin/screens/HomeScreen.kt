package br.com.fiap.edufin.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.edufin.R
import br.com.fiap.edufin.components.BarraNavegacao
import br.com.fiap.edufin.components.BarraSuperior
import br.com.fiap.edufin.components.IndicadorItem
import br.com.fiap.edufin.components.MetaItem
import br.com.fiap.edufin.components.SaudeFinanceiraCard
import br.com.fiap.edufin.model.Indicador
import br.com.fiap.edufin.model.Meta
import br.com.fiap.edufin.model.ResumoMensal
import br.com.fiap.edufin.navigation.Rota
import br.com.fiap.edufin.navigation.navegarPara
import br.com.fiap.edufin.repository.IndicadorRepository
import br.com.fiap.edufin.repository.OrcamentoRepository
import br.com.fiap.edufin.repository.getAllMetas
import br.com.fiap.edufin.ui.theme.EduFinTheme
import br.com.fiap.edufin.util.formatarData

@Composable
fun HomeScreen(navController: NavController) {
    val resumo = OrcamentoRepository.resumo
    val indicadores = IndicadorRepository.indicadores
    val metas = getAllMetas()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = stringResource(id = R.string.greeting),
                subtitulo = stringResource(id = R.string.app_name)
            )
        },
        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.Painel.caminho,
                aoNavegar = { navController.navegarPara(it) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navegarPara(Rota.Orcamento) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_entry)
                )
            }
        }
    ) { paddingValues ->
        ConteudoPainel(
            resumo = resumo,
            indicadores = indicadores,
            veioDaApi = IndicadorRepository.veioDaApi,
            metas = metas,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun ConteudoPainel(
    resumo: ResumoMensal,
    indicadores: List<Indicador>,
    veioDaApi: Boolean,
    metas: List<Meta>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SaudeFinanceiraCard(
                resumo = resumo,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            TituloSecao(
                texto = stringResource(id = R.string.today_rates),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(indicadores) { indicador ->
                    IndicadorItem(indicador = indicador)
                }
            }
        }
        indicadores.firstOrNull()?.let { indicador ->
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = stringResource(
                            id = R.string.updated_at,
                            formatarData(indicador.atualizadoEm)
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = stringResource(
                            id = if (veioDaApi) R.string.rates_source else R.string.rates_offline
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        item {
            TituloSecao(
                texto = stringResource(id = R.string.your_goals),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
        }
        items(metas) { meta ->
            MetaItem(
                meta = meta,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
internal fun TituloSecao(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Preview(name = "Tema claro", showBackground = true)
@Composable
private fun HomeScreenPreview() {
    EduFinTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(
    name = "Tema escuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenDarkPreview() {
    EduFinTheme(darkTheme = true) {
        HomeScreen(navController = rememberNavController())
    }
}
