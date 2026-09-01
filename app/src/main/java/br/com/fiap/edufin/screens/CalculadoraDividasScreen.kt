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
import br.com.fiap.edufin.navigation.navegarPara
import br.com.fiap.edufin.ui.theme.EduFinTheme
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

@Composable
fun CalculadoraDividasScreen(navController: NavController) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            BarraSuperior(
                titulo = "Calculadora de Dívidas",
                subtitulo = stringResource(id = R.string.app_name)
            )
        },

        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.CalculadoraDividas.caminho,
                aoNavegar = { navController.navegarPara(it) }
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
        valorDivida.paraDouble() != null &&
                taxaJuros.paraDouble() != null &&
                periodoMeses.toIntOrNull() != null &&
                (valorDivida.paraDouble() ?: 0.0) > 0 &&
                (taxaJuros.paraDouble() ?: 0.0) >= 0 &&
                (periodoMeses.toIntOrNull() ?: 0) > 0

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Entenda quanto sua dívida realmente pode custar.",
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
                Text(text = "Valor da dívida")
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
                Text(text = "Taxa de juros mensal")
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
                Text(text = "Período")
            },
            suffix = {
                Text(text = "meses")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                valorInicial = valorDivida.paraDouble() ?: 0.0

                val taxa =
                    (taxaJuros.paraDouble() ?: 0.0) / 100.0

                val meses =
                    periodoMeses.toIntOrNull() ?: 0

                valorFinal =
                    valorInicial * (1 + taxa).pow(meses)

                totalJuros =
                    valorFinal - valorInicial

                mostrarResultado = true
            },
            enabled = dadosValidos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calcular dívida")
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
                text = "Resultado da simulação",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Valor inicial: ${formatarMoeda(valorInicial)}"
            )

            Text(
                text = "Total de juros: ${formatarMoeda(totalJuros)}"
            )

            Text(
                text = "Valor final: ${formatarMoeda(valorFinal)}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            if (valorFinal > 0) {

                val percentualJuros =
                    (totalJuros / valorFinal) * 100

                Text(
                    text =
                        "Os juros representam " +
                                String.format(
                                    Locale("pt", "BR"),
                                    "%.1f",
                                    percentualJuros
                                ) +
                                "% do valor final da dívida.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

private fun String.paraDouble(): Double? {

    return this
        .trim()
        .replace(".", "")
        .replace(",", ".")
        .toDoubleOrNull()
}

private fun formatarMoeda(valor: Double): String {

    val formato = NumberFormat.getCurrencyInstance(
        Locale("pt", "BR")
    )

    return formato.format(valor)
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