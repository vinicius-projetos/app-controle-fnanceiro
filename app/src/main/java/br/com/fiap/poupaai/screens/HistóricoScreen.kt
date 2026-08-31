package br.com.fiap.poupaai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.poupaai.components.BarraNavegacao
import br.com.fiap.poupaai.components.BarraSuperior
import br.com.fiap.poupaai.navigation.Rota
import br.com.fiap.poupaai.navigation.navegarPara

data class MovimentacaoTeste(
    val titulo: String,
    val data: String,
    val categoria: String,
    val valor: String,
    val isReceita: Boolean
)

@Composable
fun HistóricoScreen(navController: NavController) {
    val listaTransacoes = listOf(
        MovimentacaoTeste("Salário", "10/02/2026", "Receita", "R$ 3.800,00", isReceita = true),
        MovimentacaoTeste("Supermercado", "09/02/2026", "Alimentação", "- R$ 320,50", isReceita = false),
        MovimentacaoTeste("Combustível", "08/02/2026", "Transporte", "- R$ 150,00", isReceita = false),
        MovimentacaoTeste("Netflix", "06/02/2026", "Lazer", "- R$ 55,90", isReceita = false)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = "Histórico",
                subtitulo = "Suas movimentações"
            )
        },
        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.Historico.caminho,
                aoNavegar = { destino -> navController.navegarPara(destino) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Movimentações recentes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(listaTransacoes) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = item.titulo,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${item.data} • ${item.categoria}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Text(
                            text = item.valor,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (item.isReceita) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    }
                }
            }
        }
    }
}
