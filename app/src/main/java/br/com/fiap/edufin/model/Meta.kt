package br.com.fiap.edufin.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Savings
import androidx.compose.ui.graphics.vector.ImageVector

data class Meta(
    val id: Int = 0,
    val nome: String = "",
    val valorAlvo: Double = 0.0,
    val valorGuardado: Double = 0.0,
    val icone: ImageVector = Icons.Default.Savings
) {
    val progresso: Float
        get() = if (valorAlvo <= 0.0) 0f
        else (valorGuardado / valorAlvo).toFloat().coerceIn(0f, 1f)
}
