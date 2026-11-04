package com.gcros.palavraviva.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(
    favorites: Set<String>,
    onOpen: (String, Int) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Versículos favoritos") }, navigationIcon = {
            TextButton(onClick = onBack) { Text("‹ Voltar") }
        })
    }) { padding ->
        if (favorites.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Seus favoritos aparecerão aqui.\nToque na estrela durante a leitura.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(Modifier.padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(favorites.sorted()) { ref ->
                    val parts = ref.split("|")
                    val book = parts.getOrElse(0) { "Salmos" }
                    val chapter = parts.getOrNull(1)?.toIntOrNull() ?: 1
                    Card(Modifier.fillMaxWidth().clickable { onOpen(book, chapter) }) {
                        Text("$book • capítulo $chapter", Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
