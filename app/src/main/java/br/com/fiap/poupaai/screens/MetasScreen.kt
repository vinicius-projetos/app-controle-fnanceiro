package br.com.fiap.poupaai.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.poupaai.R
import br.com.fiap.poupaai.components.BarraNavegacao
import br.com.fiap.poupaai.components.BarraSuperior
import br.com.fiap.poupaai.components.MetaItem
import br.com.fiap.poupaai.navigation.Rota
import br.com.fiap.poupaai.navigation.navegarPara
import br.com.fiap.poupaai.repository.getAllMetas

@Composable
fun MetasScreen(navController: NavController) {
    val metas = getAllMetas()

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação Nova Meta */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
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
                TituloSecao(
                    texto = "Acompanhe seus objetivos",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(metas) { meta ->
                MetaItem(
                    meta = meta,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
