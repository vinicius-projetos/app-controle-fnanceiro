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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.edufin.R
import br.com.fiap.edufin.components.BarraNavegacao
import br.com.fiap.edufin.components.BarraProgresso
import br.com.fiap.edufin.components.BarraSuperior
import br.com.fiap.edufin.components.GastoItem
import br.com.fiap.edufin.navigation.Rota
import br.com.fiap.edufin.repository.OrcamentoRepository
import br.com.fiap.edufin.ui.theme.EduFinTheme
import br.com.fiap.edufin.util.formatarMoeda
import br.com.fiap.edufin.util.formatarValor
import br.com.fiap.edufin.util.paraDouble
import kotlin.math.roundToInt

@Composable
fun OrcamentoScreen(navController: NavController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = stringResource(id = R.string.budget_title),
                subtitulo = stringResource(id = R.string.app_name)
            )
        },
        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.Orcamento.caminho,
                aoNavegar = { rota -> navController.navigate(rota.caminho) }
            )
        }
    ) { paddingValues ->
        ConteudoOrcamento(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun ConteudoOrcamento(modifier: Modifier = Modifier) {
    val gastos = OrcamentoRepository.gastos
    val resumo = OrcamentoRepository.resumo

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            CampoRenda(modifier = Modifier.padding(horizontal = 16.dp))
        }
        item {
            ResumoOrcamentoCard(modifier = Modifier.padding(horizontal = 16.dp))
        }
        item {
            TituloSecao(
                texto = stringResource(id = R.string.monthly_expenses),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
        }
        item {
            FormularioGasto(modifier = Modifier.padding(horizontal = 16.dp))
        }
        if (gastos.isEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.no_expenses),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        items(gastos, key = { it.id }) { gasto ->
            GastoItem(
                gasto = gasto,
                percentualDaRenda = if (resumo.renda > 0) {
                    (gasto.valor / resumo.renda).toFloat()
                } else {
                    0f
                },
                aoRemover = { OrcamentoRepository.removerGasto(gasto) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun CampoRenda(modifier: Modifier = Modifier) {
    var rendaTexto by remember {
        mutableStateOf(formatarValor(OrcamentoRepository.renda))
    }

    CartaoBranco(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.monthly_income),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedTextField(
            value = rendaTexto,
            onValueChange = { texto ->
                rendaTexto = texto
                OrcamentoRepository.atualizarRenda(paraDouble(texto) ?: 0.0)
            },
            label = { Text(text = stringResource(id = R.string.income_hint)) },
            prefix = { Text(text = "R$ ") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}

@Composable
private fun ResumoOrcamentoCard(modifier: Modifier = Modifier) {
    val resumo = OrcamentoRepository.resumo
    val comprometido = if (resumo.renda > 0) {
        (resumo.gastos / resumo.renda).toFloat()
    } else {
        1f
    }

    CartaoBranco(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.total_expenses),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = formatarMoeda(resumo.gastos),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.left_this_month),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = formatarMoeda(resumo.sobra),
                    style = MaterialTheme.typography.titleMedium,
                    color = resumo.nivel.cor
                )
            }
        }
        BarraProgresso(
            progresso = comprometido,
            cor = resumo.nivel.cor,
            corTrilha = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(
                id = R.string.committed_income,
                (comprometido * 100).roundToInt()
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun FormularioGasto(modifier: Modifier = Modifier) {
    var nome by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val podeAdicionar = nome.isNotBlank() && (paraDouble(valor) ?: 0.0) > 0.0

    CartaoBranco(modifier = modifier) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text(text = stringResource(id = R.string.expense_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text(text = stringResource(id = R.string.expense_value)) },
            prefix = { Text(text = "R$ ") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        Button(
            onClick = {
                OrcamentoRepository.adicionarGasto(
                    nome = nome.trim(),
                    valor = paraDouble(valor) ?: 0.0
                )
                nome = ""
                valor = ""
                focusManager.clearFocus()
            },
            enabled = podeAdicionar,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Text(
                text = stringResource(id = R.string.add_expense),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun CartaoBranco(
    modifier: Modifier = Modifier,
    conteudo: @Composable () -> Unit
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
            conteudo()
        }
    }
}

@Preview(name = "Tema claro", showBackground = true)
@Composable
private fun OrcamentoScreenPreview() {
    EduFinTheme {
        OrcamentoScreen(navController = rememberNavController())
    }
}

@Preview(
    name = "Tema escuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun OrcamentoScreenDarkPreview() {
    EduFinTheme(darkTheme = true) {
        OrcamentoScreen(navController = rememberNavController())
    }
}
