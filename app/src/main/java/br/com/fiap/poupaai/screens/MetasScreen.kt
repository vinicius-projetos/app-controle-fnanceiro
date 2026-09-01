package br.com.fiap.poupaai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun MetasScreen(navController: NavController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = stringResource(id = R.string.greeting),
                subtitulo = "Metas financeiras"
            )
        },
        bottomBar = {
            BarraNavegacao(
                rotaAtual = Rota.Metas.caminho,
                aoNavegar = { navController.navegarPara(it) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Metas financeiras",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Defina seus objetivos e acompanhe cada conquista.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nova Meta",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Nova meta",
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }
            item {
                MetaItemCard(
                    titulo = "Entrada da casa própria",
                    valorAtual = "R$ 30.000,00",
                    valorTotal = "R$ 50.000,00",
                    progresso = 0.60f,
                    porcentagemTexto = "60%"
                )
            }
            item {
                MetaItemCard(
                    titulo = "Viagem dos sonhos",
                    valorAtual = "R$ 3.500,00",
                    valorTotal = "R$ 10.000,00",
                    progresso = 0.35f,
                    porcentagemTexto = "35%"
                )
            }
            item {
                MetaItemCard(
                    titulo = "Reserva de estudos",
                    valorAtual = "R$ 3.000,00",
                    valorTotal = "R$ 15.000,00",
                    progresso = 0.20f,
                    porcentagemTexto = "20%"
                )
            }
            item {
                MetaItemCard(
                    titulo = "Reserva de emergência",
                    valorAtual = "R$ 6.000,00",
                    valorTotal = "R$ 8.000,00",
                    progresso = 0.75f,
                    porcentagemTexto = "75%"
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Guardar hoje é ter escolhas amanhã.", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF2E7D32))
                            Text(text = "Evite dívidas caras e conquiste seus objetivos com tranquilidade. 💚", fontSize = 12.sp, color = Color(0xFF4CAF50))
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = "Resumo das metas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ResumoBlocoItem(modifier = Modifier.weight(1f), label = "Metas ativas", valor = "4")
                        ResumoBlocoItem(modifier = Modifier.weight(1.3f), label = "Total necessário", valor = "R$ 42.500,00")
                        ResumoBlocoItem(modifier = Modifier.weight(1f), label = "Progresso médio", valor = "53%")
                    }
                }
            }
        }
    }
}

@Composable
fun MetaItemCard(
    titulo: String,
    valorAtual: String,
    valorTotal: String,
    progresso: Float,
    porcentagemTexto: String
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = porcentagemTexto, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { progresso },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF4CAF50),
                trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Guardado: $valorAtual", fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
                Text(text = "Objetivo: $valorTotal", fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun ResumoBlocoItem(modifier: Modifier = Modifier, label: String, valor: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = valor, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
