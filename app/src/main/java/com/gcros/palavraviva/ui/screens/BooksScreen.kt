package com.gcros.palavraviva.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class BibleBook(val name: String, val chapters: Int, val testament: String)

val bibleBooks = listOf(
    BibleBook("Gênesis", 50, "Antigo Testamento"), BibleBook("Êxodo", 40, "Antigo Testamento"),
    BibleBook("Levítico", 27, "Antigo Testamento"), BibleBook("Números", 36, "Antigo Testamento"),
    BibleBook("Deuteronômio", 34, "Antigo Testamento"), BibleBook("Josué", 24, "Antigo Testamento"),
    BibleBook("Juízes", 21, "Antigo Testamento"), BibleBook("Rute", 4, "Antigo Testamento"),
    BibleBook("1 Samuel", 31, "Antigo Testamento"), BibleBook("2 Samuel", 24, "Antigo Testamento"),
    BibleBook("1 Reis", 22, "Antigo Testamento"), BibleBook("2 Reis", 25, "Antigo Testamento"),
    BibleBook("Salmos", 150, "Antigo Testamento"), BibleBook("Provérbios", 31, "Antigo Testamento"),
    BibleBook("Eclesiastes", 12, "Antigo Testamento"), BibleBook("Cântico dos Cânticos", 8, "Antigo Testamento"),
    BibleBook("Isaías", 66, "Antigo Testamento"), BibleBook("Jeremias", 52, "Antigo Testamento"),
    BibleBook("Lamentações", 5, "Antigo Testamento"), BibleBook("Ezequiel", 48, "Antigo Testamento"),
    BibleBook("Daniel", 14, "Antigo Testamento"), BibleBook("Oséias", 14, "Antigo Testamento"),
    BibleBook("Joel", 3, "Antigo Testamento"), BibleBook("Amós", 9, "Antigo Testamento"),
    BibleBook("Abdias", 1, "Antigo Testamento"), BibleBook("Jonas", 4, "Antigo Testamento"),
    BibleBook("Miquéias", 7, "Antigo Testamento"), BibleBook("Naum", 3, "Antigo Testamento"),
    BibleBook("Habacuc", 3, "Antigo Testamento"), BibleBook("Sofonias", 3, "Antigo Testamento"),
    BibleBook("Ageu", 2, "Antigo Testamento"), BibleBook("Zacarias", 14, "Antigo Testamento"),
    BibleBook("Malaquias", 3, "Antigo Testamento"), BibleBook("Mateus", 28, "Novo Testamento"),
    BibleBook("Marcos", 16, "Novo Testamento"), BibleBook("Lucas", 24, "Novo Testamento"),
    BibleBook("João", 21, "Novo Testamento"), BibleBook("Atos", 28, "Novo Testamento"),
    BibleBook("Romanos", 16, "Novo Testamento"), BibleBook("1 Coríntios", 16, "Novo Testamento"),
    BibleBook("2 Coríntios", 13, "Novo Testamento"), BibleBook("Gálatas", 6, "Novo Testamento"),
    BibleBook("Efésios", 6, "Novo Testamento"), BibleBook("Filipenses", 4, "Novo Testamento"),
    BibleBook("Colossenses", 4, "Novo Testamento"), BibleBook("1 Tessalonicenses", 5, "Novo Testamento"),
    BibleBook("2 Tessalonicenses", 3, "Novo Testamento"), BibleBook("1 Timóteo", 6, "Novo Testamento"),
    BibleBook("2 Timóteo", 4, "Novo Testamento"), BibleBook("Tito", 3, "Novo Testamento"),
    BibleBook("Filêmon", 1, "Novo Testamento"), BibleBook("Hebreus", 13, "Novo Testamento"),
    BibleBook("Tiago", 5, "Novo Testamento"), BibleBook("1 Pedro", 5, "Novo Testamento"),
    BibleBook("2 Pedro", 3, "Novo Testamento"), BibleBook("1 João", 5, "Novo Testamento"),
    BibleBook("2 João", 1, "Novo Testamento"), BibleBook("3 João", 1, "Novo Testamento"),
    BibleBook("Judas", 1, "Novo Testamento"), BibleBook("Apocalipse", 22, "Novo Testamento")
)

@Composable
fun BooksScreen(onBookSelected: (BibleBook) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Text("Escolha um livro", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Text("Antigo e Novo Testamento", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
        }
        items(bibleBooks) { book ->
            Card(Modifier.fillMaxWidth().clickable { onBookSelected(book) }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(book.name, style = MaterialTheme.typography.titleMedium)
                        Text(book.testament, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    }
                    Text("${book.chapters} capítulos", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}