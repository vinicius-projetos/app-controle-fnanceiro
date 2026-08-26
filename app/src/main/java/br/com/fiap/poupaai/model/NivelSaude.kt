package br.com.fiap.poupaai.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import br.com.fiap.poupaai.R
import br.com.fiap.poupaai.ui.theme.StatusCritical
import br.com.fiap.poupaai.ui.theme.StatusGood
import br.com.fiap.poupaai.ui.theme.StatusWarning

enum class NivelSaude(
    @param:StringRes val descricao: Int,
    val cor: Color
) {
    BOA(R.string.health_good, StatusGood),
    ATENCAO(R.string.health_warning, StatusWarning),
    CRITICA(R.string.health_critical, StatusCritical)
}
