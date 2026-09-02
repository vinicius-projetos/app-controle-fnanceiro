package br.com.fiap.edufin.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.edufin.R
import br.com.fiap.edufin.components.BarraNavegacao
import br.com.fiap.edufin.components.BarraSuperior
import br.com.fiap.edufin.navigation.Rota
import br.com.fiap.edufin.ui.theme.EduFinTheme
import br.com.fiap.edufin.util.formatarMoeda
import br.com.fiap.edufin.util.formatarValor
import br.com.fiap.edufin.util.paraDouble
import kotlin.math.pow

@Composable
fun CalculadoraDividasScreen(navController: NavController) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            BarraSuperior(
                titulo = stringResource(id = R.string.debts_title),
                subtitulo = stringResource(id = R.string.app_name)
            )
        },

        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.CalculadoraDividas.caminho,
                aoNavegar = { rota -> navController.navigate(rota.caminho) }
            )
        }

    ) { paddingValues ->

        CalculadoraDividasConteudo(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun CalculadoraDividasConteudo(
    modifier: Modifier = Modifier
) {

    var valorDivida by remember { mutableStateOf("") }
    var taxaJuros by remember { mutableStateOf("") }
    var periodoMeses by remember { mutableStateOf("") }

    var valorInicial by remember { mutableStateOf(0.0) }
    var totalJuros by remember { mutableStateOf(0.0) }
    var valorFinal by remember { mutableStateOf(0.0) }
    var mostrarResultado by remember { mutableStateOf(false) }

    val dadosValidos =
        (paraDouble(valorDivida) ?: 0.0) > 0 &&
                (paraDouble(taxaJuros) ?: -1.0) >= 0 &&
                (periodoMeses.toIntOrNull() ?: 0) > 0

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = stringResource(id = R.string.debts_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        OutlinedTextField(
            value = valorDivida,
            onValueChange = {
                valorDivida = it
                mostrarResultado = false
            },
            label = {
                Text(text = stringResource(id = R.string.debt_value))
            },
            prefix = {
                Text(text = "R$ ")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = taxaJuros,
            onValueChange = {
                taxaJuros = it
                mostrarResultado = false
            },
            label = {
                Text(text = stringResource(id = R.string.debt_rate))
            },
            suffix = {
                Text(text = "%")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = periodoMeses,
            onValueChange = {
                periodoMeses = it
                mostrarResultado = false
            },
            label = {
                Text(text = stringResource(id = R.string.debt_period))
            },
            suffix = {
                Text(text = stringResource(id = R.string.debt_months))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                valorInicial = paraDouble(valorDivida) ?: 0.0

                val taxa = (paraDouble(taxaJuros) ?: 0.0) / 100.0

                val meses = periodoMeses.toIntOrNull() ?: 0

                valorFinal = valorInicial * (1 + taxa).pow(meses)

                totalJuros = valorFinal - valorInicial

                mostrarResultado = true
            },
            enabled = dadosValidos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.debt_calculate))
        }

        if (mostrarResultado) {

            ResultadoDivida(
                valorInicial = valorInicial,
                totalJuros = totalJuros,
                valorFinal = valorFinal
            )
        }
    }
}

@Composable
private fun ResultadoDivida(
    valorInicial: Double,
    totalJuros: Double,
    valorFinal: Double
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = stringResource(id = R.string.debt_result),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    id = R.string.debt_initial,
                    formatarMoeda(valorInicial)
                )
            )

            Text(
                text = stringResource(
                    id = R.string.debt_interest,
                    formatarMoeda(totalJuros)
                )
            )

            Text(
                text = stringResource(
                    id = R.string.debt_final,
                    formatarMoeda(valorFinal)
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            if (valorFinal > 0) {

                val percentualJuros = (totalJuros / valorFinal) * 100

                Text(
                    text = stringResource(
                        id = R.string.debt_interest_share,
                        formatarValor(percentualJuros)
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(
    name = "Tema claro",
    showBackground = true
)
@Composable
private fun CalculadoraDividasScreenPreview() {

    EduFinTheme {

        CalculadoraDividasScreen(
            navController = rememberNavController()
        )
    }
}

@Preview(
    name = "Tema escuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CalculadoraDividasScreenDarkPreview() {

    EduFinTheme(
        darkTheme = true
    ) {

        CalculadoraDividasScreen(
            navController = rememberNavController()
        )
    }
}
