package com.gcros.palavraviva.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.util.zip.ZipInputStream

private data class BibleVerse(val number: Int, val text: String)

private val bookCodes = mapOf(
    "Gênesis" to "GEN", "Êxodo" to "EXO", "Levítico" to "LEV",
    "Números" to "NUM", "Deuteronômio" to "DEU", "Josué" to "JOS",
    "Juízes" to "JDG", "Rute" to "RUT", "1 Samuel" to "1SA",
    "2 Samuel" to "2SA", "1 Reis" to "1KI", "2 Reis" to "2KI",
    "1 Crônicas" to "1CH", "2 Crônicas" to "2CH", "Esdras" to "EZR",
    "Neemias" to "NEH", "Tobias" to "TOB", "Judite" to "JDT",
    "Ester" to "EST", "1 Macabeus" to "1MA", "2 Macabeus" to "2MA",
    "Jó" to "JOB", "Salmos" to "PSA", "Provérbios" to "PRO",
    "Eclesiastes" to "ECC", "Cântico dos Cânticos" to "SNG",
    "Sabedoria" to "WIS", "Eclesiástico" to "SIR", "Isaías" to "ISA",
    "Jeremias" to "JER", "Lamentações" to "LAM", "Baruc" to "BAR",
    "Ezequiel" to "EZK", "Daniel" to "DAN", "Oséias" to "HOS",
    "Joel" to "JOL", "Amós" to "AMO", "Abdias" to "OBA",
    "Jonas" to "JON", "Miquéias" to "MIC", "Naum" to "NAM",
    "Habacuc" to "HAB", "Sofonias" to "ZEP", "Ageu" to "HAG",
    "Zacarias" to "ZEC", "Malaquias" to "MAL", "Mateus" to "MAT",
    "Marcos" to "MRK", "Lucas" to "LUK", "João" to "JHN",
    "Atos dos Apóstolos" to "ACT", "Romanos" to "ROM",
    "1 Coríntios" to "1CO", "2 Coríntios" to "2CO", "Gálatas" to "GAL",
    "Efésios" to "EPH", "Filipenses" to "PHP", "Colossenses" to "COL",
    "1 Tessalonicenses" to "1TH", "2 Tessalonicenses" to "2TH",
    "1 Timóteo" to "1TI", "2 Timóteo" to "2TI", "Tito" to "TIT",
    "Filêmon" to "PHM", "Hebreus" to "HEB", "Tiago" to "JAS",
    "1 Pedro" to "1PE", "2 Pedro" to "2PE", "1 João" to "1JN",
    "2 João" to "2JN", "3 João" to "3JN", "Judas" to "JUD",
    "Apocalipse" to "REV"
)

/**
 * Os capítulos da BPM são arquivos de texto separados dentro do ZIP incluído
 * em app/src/main/assets. Não há chamadas de rede: a leitura funciona offline.
 * Cada arquivo contém título, número do capítulo e um parágrafo por versículo.
 */
private suspend fun loadBibleChapter(
    context: Context,
    book: BibleBook,
    chapter: Int
): List<BibleVerse> = withContext(Dispatchers.IO) {
    val code = bookCodes[book.name]
        ?: throw IllegalArgumentException("Código do livro não encontrado: ${book.name}")

    var usfmText: String? = null
    context.assets.open("porbrbsl_usfm.zip").use { asset ->
        ZipInputStream(asset).use { zip ->
            var entry = zip.nextEntry
            while (entry != null) {
                if (!entry.isDirectory && entry.name.endsWith("_$code.usfm", ignoreCase = true)) {
                    usfmText = zip.readBytes().toString(Charsets.UTF_8)
                    break
                }
                zip.closeEntry()
                entry = zip.nextEntry
            }
        }
    }

    val source = usfmText
        ?: throw IllegalStateException("O livro ${book.name} não foi encontrado no arquivo bíblico offline.")

    val verses = linkedMapOf<Int, StringBuilder>()
    var currentChapter = 0
    var currentVerse: Int? = null

    val chapterPattern = Regex("""^\\c\\s+(\\d+)""")
    val versePattern = Regex("""^\\v\\s+(\\d+)(?:-\\d+)?\\s*(.*)$""")

    source.removePrefix("\uFEFF").lineSequence().forEach { rawLine ->
        val line = rawLine.trim()
        if (line.isEmpty()) return@forEach

        val chapterMatch = chapterPattern.find(line)
        if (chapterMatch != null) {
            currentChapter = chapterMatch.groupValues[1].toIntOrNull() ?: 0
            currentVerse = null
            return@forEach
        }

        if (currentChapter != chapter) return@forEach

        val verseMatch = versePattern.find(line)
        if (verseMatch != null) {
            val number = verseMatch.groupValues[1].toIntOrNull() ?: return@forEach
            val text = cleanUsfmText(verseMatch.groupValues[2])
            verses[number] = StringBuilder(text)
            currentVerse = number
            return@forEach
        }

        // Linhas de continuação podem quebrar um versículo comprido.
        if (currentVerse != null && !line.startsWith("\\")) {
            val continuation = cleanUsfmText(line)
            if (continuation.isNotBlank()) {
                val builder = verses[currentVerse] ?: return@forEach
                if (builder.isNotEmpty()) builder.append(' ')
                builder.append(continuation)
            }
        }
    }

    verses.mapNotNull { (number, text) ->
        text.toString().trim().takeIf { it.isNotBlank() }?.let { BibleVerse(number, it) }
    }.also {
        if (it.isEmpty()) {
            throw IllegalStateException("Nenhum versículo foi encontrado em ${book.name} $chapter no arquivo USFM.")
        }
    }
}

private fun cleanUsfmText(value: String): String {
    return value
        .replace(Regex("""\\f .*?\\f\\*"""), "")
        .replace(Regex("""\\x .*?\\x\\*"""), "")
        .replace(Regex("""\\w ([^|]+)\\|[^\\\\]*?\\w\\*"""), "$1")
        .replace(Regex("""\\[a-zA-Z0-9+]+\\*?"""), "")
        .replace(Regex("""\\[+]?"""), "")
        .replace(Regex("""\\s+"""), " ")
        .trim()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    book: BibleBook,
    chapter: Int,
    onBack: () -> Unit,
    onChapterChange: (Int) -> Unit,
    isFavorite: Boolean = false,
    onToggleFavorite: () -> Unit = {}
) {
    val context = LocalContext.current
    var verses by remember(book.name, chapter) { mutableStateOf<List<BibleVerse>>(emptyList()) }
    var loading by remember(book.name, chapter) { mutableStateOf(true) }
    var error by remember(book.name, chapter) { mutableStateOf<String?>(null) }
    var retryCount by remember(book.name, chapter) { mutableIntStateOf(0) }

    LaunchedEffect(book.name, chapter, retryCount) {
        loading = true
        error = null
        verses = emptyList()
        try {
            verses = loadBibleChapter(context, book, chapter)
        } catch (exception: Exception) {
            error = exception.message ?: "Não foi possível abrir este capítulo."
        } finally {
            loading = false
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("${book.name} • Cap. $chapter") },
            navigationIcon = { TextButton(onClick = onBack) { Text("‹ Voltar") } },
            actions = {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        if (isFavorite) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Favoritar capítulo"
                    )
                }
                if (chapter > 1) TextButton(onClick = { onChapterChange(chapter - 1) }) { Text("‹") }
                if (chapter < book.chapters) TextButton(onClick = { onChapterChange(chapter + 1) }) { Text("›") }
            }
        )
    }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                    Text("Bíblia Portuguesa Mundial (BPM) • leitura offline")
                    Text(
                        "Tradução em revisão, sem imprimatur. Fonte: eBible.org.",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            when {
                loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                error != null -> Column(
                    Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(error ?: "Erro ao abrir o capítulo.")
                    Button(onClick = { retryCount++ }) { Text("Tentar novamente") }
                }
                else -> LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(verses, key = { it.number }) { verse ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                "${verse.number}",
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.width(30.dp)
                            )
                            Text(verse.text, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
