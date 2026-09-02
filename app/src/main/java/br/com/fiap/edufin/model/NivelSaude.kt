package br.com.fiap.edufin.model

import androidx.compose.ui.graphics.Color
import br.com.fiap.edufin.R
import br.com.fiap.edufin.ui.theme.StatusCritical
import br.com.fiap.edufin.ui.theme.StatusGood
import br.com.fiap.edufin.ui.theme.StatusWarning

enum class NivelSaude(
    val descricao: Int,
    val cor: Color
) {
    BOA(R.string.health_good, StatusGood),
    ATENCAO(R.string.health_warning, StatusWarning),
    CRITICA(R.string.health_critical, StatusCritical)
}
