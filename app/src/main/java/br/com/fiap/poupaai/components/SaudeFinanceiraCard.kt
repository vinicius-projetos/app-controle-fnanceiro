package br.com.fiap.poupaai.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.poupaai.R
import br.com.fiap.poupaai.model.NivelSaude
import br.com.fiap.poupaai.model.ResumoMensal
import br.com.fiap.poupaai.util.formatarMoeda

@Composable
fun SaudeFinanceiraCard(
    resumo: ResumoMensal,
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
                    text = stringResource(id = R.string.financial_health),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                EtiquetaNivel(nivel = resumo.nivel)
            }
            Text(
                text = stringResource(id = R.string.left_this_month),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = formatarMoeda(resumo.sobra),
                style = MaterialTheme.typography.titleLarge,
                color = if (resumo.sobra < 0) resumo.nivel.cor
                else MaterialTheme.colorScheme.onSurface
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ValorResumo(
                    rotulo = stringResource(id = R.string.income),
                    valor = formatarMoeda(resumo.renda)
                )
                ValorResumo(
                    rotulo = stringResource(id = R.string.expenses),
                    valor = formatarMoeda(resumo.gastos)
                )
            }
        }
    }
}

@Composable
private fun EtiquetaNivel(nivel: NivelSaude) {
    Surface(
        color = nivel.cor.copy(alpha = 0.14f),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = nivel.cor,
                shape = CircleShape,
                modifier = Modifier.size(8.dp),
                content = { }
            )
            Text(
                text = stringResource(id = nivel.descricao),
                style = MaterialTheme.typography.labelMedium,
                color = nivel.cor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun ValorResumo(rotulo: String, valor: String, cor: Color? = null) {
    Column {
        Text(
            text = rotulo,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.labelMedium,
            color = cor ?: MaterialTheme.colorScheme.onSurface
        )
    }
}
