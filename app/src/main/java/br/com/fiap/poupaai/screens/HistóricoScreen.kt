package br.com.fiap.poupaai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.poupaai.R
import br.com.fiap.poupaai.components.BarraNavegacao
import br.com.fiap.poupaai.components.BarraSuperior
import br.com.fiap.poupaai.navigation.Rota
import br.com.fiap.poupaai.navigation.navegarPara

@Composable
fun HistóricoScreen(navController: NavController) {
    val filtros = listOf("Todas", "Receitas", "Despesas", "Simulações", "Metas")

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = stringResource(id = R.string.greeting),
                subtitulo = "Histórico"
            )
        },
        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.Historico.caminho,
                aoNavegar = { navController.navegarPara(it) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Text(text = "Histórico", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = "Acompanhe suas movimentações, simulações e metas.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                ) {
                    items(filtros) { filtro ->
                        val selecionado = filtro == "Todas"
                        FilterChip(
                            selected = selecionado,
                            onClick = { },
                            label = { Text(filtro, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Agosto/2026",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
            }

            item {
                CardSimulacaoHistorico(
                    titulo = "Simulação de cartão rotativo",
                    data = "01/08/2026",
                    simulado = "R$ 1.000,00",
                    juros = "R$ 418,00",
                    total = "R$ 1.418,00"
                )
            }
            item {
                CardSimulacaoHistorico(
                    titulo = "Simulação de empréstimo",
                    data = "28/07/2026",
                    simulado = "R$ 5.000,00",
                    juros = "R$ 1.840,00",
                    total = "R$ 6.840,00"
                )
            }

            item {
                CardMovimentacaoSimples(titulo = "Salário", data = "25/07/2026", cat = "Receita", valor = "+ R$ 3.800,00", cor = Color(0xFF2E7D32))
            }
            item {
                CardMovimentacaoSimples(titulo = "Aporte na meta: Reserva de emergência", data = "24/07/2026", cat = "Meta", valor = "+ R$ 300,00", cor = Color(0xFF2E7D32))
            }
            item {
                CardMovimentacaoSimples(titulo = "Supermercado", data = "20/07/2026", cat = "Alimentação", valor = "- R$ 320,50", cor = Color(0xFFC62828))
            }
        }
    }
}

@Composable
fun CardSimulacaoHistorico(titulo: String, data: String, simulado: String, juros: String, total: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = titulo, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(text = "$data  •  Simulação", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
                Text(text = "- $total", fontWeight = FontWeight.Bold, color = Color(0xFFC62828), fontSize = 14.sp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF5F5F5))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "Valor simulado: $simulado", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                Text(text = "Juros: $juros", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                Text(text = "Total a pagar: $total", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Composable
fun CardMovimentacaoSimples(titulo: String, data: String, cat: String, valor: String, cor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = titulo, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(text = "$data  •  $cat", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
            Text(text = valor, fontWeight = FontWeight.Bold, color = cor, fontSize = 14.sp)
        }
    }
}
