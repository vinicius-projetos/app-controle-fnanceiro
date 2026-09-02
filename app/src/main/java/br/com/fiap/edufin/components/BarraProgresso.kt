package br.com.fiap.edufin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Barra de progresso própria em vez da LinearProgressIndicator porque a versão
 * do Material 3 desenha um recorte e um ponto no fim que poluem os cards.
 */
@Composable
fun BarraProgresso(
    progresso: Float,
    cor: Color,
    corTrilha: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(CircleShape)
            .background(corTrilha)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progresso.coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(CircleShape)
                .background(cor)
        )
    }
}
