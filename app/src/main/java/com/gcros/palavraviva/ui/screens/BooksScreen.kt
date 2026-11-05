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

private const val OT = "Antigo Testamento"
private const val NT = "Novo Testamento"

/** Canonização católica: 46 livros no Antigo Testamento e 27 no Novo. */
val bibleBooks = listOf(
    BibleBook("Gênesis", 50, OT),
    BibleBook("Êxodo", 40, OT),
    BibleBook("Levítico", 27, OT),
    BibleBook("Números", 36, OT),
    BibleBook("Deuteronômio", 34, OT),
    BibleBook("Josué", 24, OT),
    BibleBook("Juízes", 21, OT),
    BibleBook("Rute", 4, OT),
    BibleBook("1 Samuel", 31, OT),
    BibleBook("2 Samuel", 24, OT),
    BibleBook("1 Reis", 22, OT),
    BibleBook("2 Reis", 25, OT),
    BibleBook("1 Crônicas", 29, OT),
    BibleBook("2 Crônicas", 36, OT),
    BibleBook("Esdras", 10, OT),
    BibleBook("Neemias", 13, OT),
    BibleBook("Tobias", 14, OT),
    BibleBook("Judite", 16, OT),
    BibleBook("Ester", 16, OT),
    BibleBook("1 Macabeus", 16, OT),
    BibleBook("2 Macabeus", 15, OT),
    BibleBook("Jó", 42, OT),
    BibleBook("Salmos", 150, OT),
    BibleBook("Provérbios", 31, OT),
    BibleBook("Eclesiastes", 12, OT),
    BibleBook("Cântico dos Cânticos", 8, OT),
    BibleBook("Sabedoria", 19, OT),
    BibleBook("Eclesiástico", 51, OT),
    BibleBook("Isaías", 66, OT),
    BibleBook("Jeremias", 52, OT),
    BibleBook("Lamentações", 5, OT),
    BibleBook("Baruc", 6, OT),
    BibleBook("Ezequiel", 48, OT),
    BibleBook("Daniel", 14, OT),
    BibleBook("Oséias", 14, OT),
    BibleBook("Joel", 3, OT),
    BibleBook("Amós", 9, OT),
    BibleBook("Abdias", 1, OT),
    BibleBook("Jonas", 4, OT),
    BibleBook("Miquéias", 7, OT),
    BibleBook("Naum", 3, OT),
    BibleBook("Habacuc", 3, OT),
    BibleBook("Sofonias", 3, OT),
    BibleBook("Ageu", 2, OT),
    BibleBook("Zacarias", 14, OT),
    BibleBook("Malaquias", 3, OT),

    BibleBook("Mateus", 28, NT),
    BibleBook("Marcos", 16, NT),
    BibleBook("Lucas", 24, NT),
    BibleBook("João", 21, NT),
    BibleBook("Atos dos Apóstolos", 28, NT),
    BibleBook("Romanos", 16, NT),
    BibleBook("1 Coríntios", 16, NT),
    BibleBook("2 Coríntios", 13, NT),
    BibleBook("Gálatas", 6, NT),
    BibleBook("Efésios", 6, NT),
    BibleBook("Filipenses", 4, NT),
    BibleBook("Colossenses", 4, NT),
    BibleBook("1 Tessalonicenses", 5, NT),
    BibleBook("2 Tessalonicenses", 3, NT),
    BibleBook("1 Timóteo", 6, NT),
    BibleBook("2 Timóteo", 4, NT),
    BibleBook("Tito", 3, NT),
    BibleBook("Filêmon", 1, NT),
    BibleBook("Hebreus", 13, NT),
    BibleBook("Tiago", 5, NT),
    BibleBook("1 Pedro", 5, NT),
    BibleBook("2 Pedro", 3, NT),
    BibleBook("1 João", 5, NT),
    BibleBook("2 João", 1, NT),
    BibleBook("3 João", 1, NT),
    BibleBook("Judas", 1, NT),
    BibleBook("Apocalipse", 22, NT)
)

@Composable
fun BooksScreen(onBookSelected: (BibleBook) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Escolha um livro",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text("Bíblia católica • 73 livros", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
        }
        items(bibleBooks) { book ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onBookSelected(book) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(book.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            book.testament,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text("${book.chapters} capítulos", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
