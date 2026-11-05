package com.gcros.palavraviva.ui.screens

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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

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

private suspend fun loadBibleChapter(book: BibleBook, chapter: Int): List<BibleVerse> =
    withContext(Dispatchers.IO) {
        val code = bookCodes[book.name]
            ?: throw IllegalArgumentException("Código do livro não encontrado.")
        val url = URL("https://bible.helloao.org/api/porbrbsl/$code/$chapter.simple.json")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 15000
            readTimeout = 20000
            setRequestProperty("Accept", "application/json")
        }
        try {
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException("Fonte bíblica indisponível (HTTP ${connection.responseCode}).")
            }
            val body = connection.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            val root = JSONObject(body)
            val chapterObject = root.optJSONObject("chapter")
                ?: throw IllegalStateException("Formato de capítulo não reconhecido.")
            val content = chapterObject.optJSONArray("content") ?: JSONArray()
            buildList {
                for (index in 0 until content.length()) {
                    val item = content.optJSONObject(index) ?: continue
                    if (item.optString("type") != "verse") continue
                    val number = item.optInt("number", 0)
                    val text = item.optString("text", "").trim()
                    if (number > 0 && text.isNotBlank()) add(BibleVerse(number, text))
                }
            }
        } finally {
            connection.disconnect()
        }
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
    var verses by remember(book.name, chapter) { mutableStateOf<List<BibleVerse>>(emptyList()) }
    var loading by remember(book.name, chapter) { mutableStateOf(true) }
    var error by remember(book.name, chapter) { mutableStateOf<String?>(null) }
    var retryCount by remember(book.name, chapter) { mutableIntStateOf(0) }

    LaunchedEffect(book.name, chapter, retryCount) {
        loading = true
        error = null
        verses = emptyList()
        try {
            verses = loadBibleChapter(book, chapter)
            if (verses.isEmpty()) error = "Este capítulo não trouxe versículos da fonte."
        } catch (exception: Exception) {
            error = exception.message ?: "Não foi possível carregar o capítulo."
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
                    Text("Bíblia Portuguesa Mundial (BPM) • domínio público")
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
                    Text(error ?: "Erro ao carregar.")
                    Text("Verifique sua conexão com a internet e tente novamente.")
                    Button(onClick = {
                        // Re-execução do carregamento pelo efeito ao alterar a chave.
                        retryCount++
                    }) { Text("Tentar novamente") }
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
