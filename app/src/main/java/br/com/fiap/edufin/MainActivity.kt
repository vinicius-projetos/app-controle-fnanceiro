package br.com.fiap.edufin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.edufin.navigation.Rota
import br.com.fiap.edufin.repository.IndicadorRepository
import br.com.fiap.edufin.screens.CalculadoraDividasScreen
import br.com.fiap.edufin.screens.HistóricoScreen
import br.com.fiap.edufin.screens.HomeScreen
import br.com.fiap.edufin.screens.MetasScreen
import br.com.fiap.edufin.screens.OrcamentoScreen
import br.com.fiap.edufin.screens.SimuladorInvestimentosScreen
import br.com.fiap.edufin.ui.theme.EduFinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EduFinTheme {
                EduFinApp()
            }
        }
    }
}

@Composable
fun EduFinApp() {

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        IndicadorRepository.atualizar()
    }

    NavHost(
        navController = navController,
        startDestination = Rota.Painel.caminho
    ) {
        composable(Rota.Painel.caminho) {
            HomeScreen(navController)
        }

        composable(Rota.Orcamento.caminho) {
            OrcamentoScreen(navController)
        }

        composable(Rota.Metas.caminho) {
            MetasScreen(navController)
        }

        composable(Rota.Historico.caminho) {
            HistóricoScreen(navController)
        }

        composable(Rota.CalculadoraDividas.caminho) {
            CalculadoraDividasScreen(navController)
        }

        composable(Rota.SimuladorInvestimentos.caminho) {
            SimuladorInvestimentosScreen(navController)
        }
    }
}
