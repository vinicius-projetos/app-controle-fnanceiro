package br.com.fiap.edufin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.edufin.R
import br.com.fiap.edufin.model.Simulacao
import br.com.fiap.edufin.util.formatarMoeda

@Composable
fun SimulacaoItem(
    simulacao: Simulacao,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pluralStringResource(
                        id = R.plurals.years,
                        count = simulacao.anos,
                        simulacao.anos
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formatarMoeda(simulacao.totalFinal),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            BarraProgresso(
                progresso = simulacao.proporcaoInvestida,
                cor = MaterialTheme.colorScheme.primary,
                corTrilha = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LegendaValor(
                    cor = MaterialTheme.colorScheme.primary,
                    rotulo = stringResource(id = R.string.you_save),
                    valor = formatarMoeda(simulacao.totalInvestido)
                )
                LegendaValor(
                    cor = MaterialTheme.colorScheme.tertiary,
                    rotulo = stringResource(id = R.string.it_yields),
                    valor = formatarMoeda(simulacao.juros),
                    alinhamento = Alignment.End
                )
            }
        }
    }
}

@Composable
private fun LegendaValor(
    cor: Color,
    rotulo: String,
    valor: String,
    alinhamento: Alignment.Horizontal = Alignment.Start
) {
    Column(horizontalAlignment = alinhamento) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = cor,
                shape = CircleShape,
                modifier = Modifier.size(8.dp),
                content = { }
            )
            Text(
                text = rotulo,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
        Text(
            text = valor,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
