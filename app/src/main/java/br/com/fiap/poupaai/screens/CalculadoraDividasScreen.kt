package br.com.fiap.poupaai.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.poupaai.components.BarraSuperior
import br.com.fiap.poupaai.ui.theme.PoupaAiTheme

@Composable
fun CalculadoraDividasScreen(navController: NavController) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            BarraSuperior(
                titulo = "Calculadora de Dívidas",
                subtitulo = "Poupa Aí"
            )
        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Calculadora de Dívidas",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(
    name = "Tema claro",
    showBackground = true
)
@Composable
private fun CalculadoraDividasScreenPreview() {
    PoupaAiTheme {
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
    PoupaAiTheme(darkTheme = true) {
        CalculadoraDividasScreen(
            navController = rememberNavController()
        )
    }
}