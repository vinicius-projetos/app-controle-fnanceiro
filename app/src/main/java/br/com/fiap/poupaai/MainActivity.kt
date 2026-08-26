package br.com.fiap.poupaai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.poupaai.navigation.Rota
import br.com.fiap.poupaai.screens.HomeScreen
import br.com.fiap.poupaai.screens.OrcamentoScreen
import br.com.fiap.poupaai.ui.theme.PoupaAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PoupaAiTheme {
                PoupaAiApp()
            }
        }
    }
}

@Composable
fun PoupaAiApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rota.Painel.caminho
    ) {
        composable(Rota.Painel.caminho) { HomeScreen(navController) }
        composable(Rota.Orcamento.caminho) { OrcamentoScreen(navController) }
    }
}
