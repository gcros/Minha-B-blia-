package com.gcros.palavraviva.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val PalavraVivaColors = lightColorScheme(
    primary = MarromProfundo,
    onPrimary = PergaminhoClaro,
    secondary = DouradoEnvelhecido,
    onSecondary = Tinta,
    background = PergaminhoClaro,
    onBackground = Tinta,
    surface = Pergaminho,
    onSurface = Tinta
)

@Composable
fun PalavraVivaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PalavraVivaColors,
        content = content
    )
}
