package com.gcros.palavraviva.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReaderScreen(book: BibleBook, chapter: Int, onBack: () -> Unit, onChapterChange: (Int) -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("${book.name} • Cap. $chapter") }, navigationIcon = {
            TextButton(onClick = onBack) { Text("‹ Voltar") }
        }, actions = {
            if (chapter > 1) TextButton(onClick = { onChapterChange(chapter - 1) }) { Text("‹") }
            if (chapter < book.chapters) TextButton(onClick = { onChapterChange(chapter + 1) }) { Text("›") }
        })
    }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
                Text("Tradução Ave-Maria • texto a integrar com autorização/licença", Modifier.fillMaxWidth().padding(12.dp), style = MaterialTheme.typography.labelMedium)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items((1..30).toList()) { verse ->
                    Row(verticalAlignment = Alignment.Top) {
                        Text("$verse", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelMedium, modifier = Modifier.width(30.dp))
                        Text(if (verse == 1) "O texto deste capítulo será disponibilizado após integração de uma fonte licenciada." else " ", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
