package br.com.fiap.poupaai.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.School
import br.com.fiap.poupaai.model.Meta

fun getAllMetas() = listOf(
    Meta(
        id = 1,
        nome = "Reserva de emergência",
        valorAlvo = 3000.00,
        valorGuardado = 1150.00,
        icone = Icons.Default.HealthAndSafety
    ),
    Meta(
        id = 2,
        nome = "Curso técnico",
        valorAlvo = 1200.00,
        valorGuardado = 840.00,
        icone = Icons.Default.School
    ),
    Meta(
        id = 3,
        nome = "Notebook para estudar",
        valorAlvo = 2500.00,
        valorGuardado = 375.00,
        icone = Icons.Default.Laptop
    )
)
