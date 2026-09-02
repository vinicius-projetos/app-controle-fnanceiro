package br.com.fiap.edufin.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.edufin.R
import br.com.fiap.edufin.components.BarraNavegacao
import br.com.fiap.edufin.components.BarraSuperior
import br.com.fiap.edufin.components.SimulacaoItem
import br.com.fiap.edufin.model.Indicador
import br.com.fiap.edufin.navigation.Rota
import br.com.fiap.edufin.repository.IndicadorRepository
import br.com.fiap.edufin.ui.theme.EduFinTheme
import br.com.fiap.edufin.util.formatarMoeda
import br.com.fiap.edufin.util.formatarPercentual
import br.com.fiap.edufin.util.formatarValor
import br.com.fiap.edufin.util.paraDouble
import br.com.fiap.edufin.util.simularAportes

private val APORTES_SUGERIDOS = listOf(50.0, 100.0, 200.0)

@Composable
fun SimuladorInvestimentosScreen(navController: NavController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = stringResource(id = R.string.simulator_title),
                subtitulo = stringResource(id = R.string.app_name)
            )
        },
        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.SimuladorInvestimentos.caminho,
                aoNavegar = { rota -> navController.navigate(rota.caminho) }
            )
        }
    ) { paddingValues ->
        ConteudoSimulador(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun ConteudoSimulador(modifier: Modifier = Modifier) {
    val indicadores = IndicadorRepository.indicadores
    var aporteTexto by rememberSaveable { mutableStateOf(formatarValor(50.0)) }
    var indiceIndicador by rememberSaveable { mutableIntStateOf(0) }

    val indicador = indicadores.getOrNull(indiceIndicador) ?: return
    val simulacoes = simularAportes(
        aporteMensal = paraDouble(aporteTexto) ?: 0.0,
        taxaAnual = indicador.valor
    )

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            CampoAporte(
                valor = aporteTexto,
                aoMudar = { aporteTexto = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            TituloSecao(
                texto = stringResource(id = R.string.where_to_invest),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
        }
        item {
            SeletorIndicador(
                indicadores = indicadores,
                selecionado = indicador,
                aoSelecionar = { indiceIndicador = indicadores.indexOf(it) }
            )
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.rate_summary,
                        indicador.nome,
                        formatarPercentual(indicador.valor)
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(
                        id = if (IndicadorRepository.veioDaApi) {
                            R.string.rates_source
                        } else {
                            R.string.rates_offline
                        }
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        item {
            TituloSecao(
                texto = stringResource(id = R.string.projection),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
        }
        items(simulacoes) { simulacao ->
            SimulacaoItem(
                simulacao = simulacao,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            Text(
                text = stringResource(id = R.string.simulator_disclaimer),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun CampoAporte(
    valor: String,
    aoMudar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(id = R.string.monthly_contribution),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            OutlinedTextField(
                value = valor,
                onValueChange = aoMudar,
                label = { Text(text = stringResource(id = R.string.contribution_hint)) },
                prefix = { Text(text = "R$ ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Row(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                APORTES_SUGERIDOS.forEach { sugestao ->
                    Etiqueta(
                        texto = formatarMoeda(sugestao),
                        selecionada = paraDouble(valor) == sugestao,
                        aoClicar = { aoMudar(formatarValor(sugestao)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SeletorIndicador(
    indicadores: List<Indicador>,
    selecionado: Indicador,
    aoSelecionar: (Indicador) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(indicadores) { indicador ->
            Etiqueta(
                texto = indicador.sigla,
                selecionada = indicador == selecionado,
                aoClicar = { aoSelecionar(indicador) }
            )
        }
    }
}

@Composable
private fun Etiqueta(
    texto: String,
    selecionada: Boolean,
    aoClicar: () -> Unit
) {
    FilterChip(
        selected = selecionada,
        onClick = aoClicar,
        label = { Text(text = texto, style = MaterialTheme.typography.bodySmall) },
        border = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.secondary,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(name = "Tema claro", showBackground = true)
@Composable
private fun SimuladorInvestimentosScreenPreview() {
    EduFinTheme {
        SimuladorInvestimentosScreen(navController = rememberNavController())
    }
}

@Preview(
    name = "Tema escuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SimuladorInvestimentosScreenDarkPreview() {
    EduFinTheme(darkTheme = true) {
        SimuladorInvestimentosScreen(navController = rememberNavController())
    }
}
