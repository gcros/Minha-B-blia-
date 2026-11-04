package com.gcros.palavraviva.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.gcros.palavraviva.data.PreferencesRepository
import com.gcros.palavraviva.ui.screens.*

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    val context = LocalContext.current
    val prefs = remember { PreferencesRepository(context) }
    val favorites by prefs.favorites.collectAsState()
    var selectedBook by remember { mutableStateOf(bibleBooks.first()) }
    var selectedChapter by remember { mutableIntStateOf(1) }

    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onOpenBible = { nav.navigate("books") },
                onListen = { nav.navigate("audio") },
                onSearch = { nav.navigate("search") },
                onFavorites = { nav.navigate("favorites") }
            )
        }
        composable("books") {
            BooksScreen { book ->
                selectedBook = book
                selectedChapter = 1
                nav.navigate("reader")
            }
        }
        composable("reader") {
            val reference = "${selectedBook.name}|$selectedChapter"
            ReaderScreen(
                book = selectedBook,
                chapter = selectedChapter,
                onBack = { nav.popBackStack() },
                onChapterChange = { selectedChapter = it },
                isFavorite = reference in favorites,
                onToggleFavorite = { prefs.toggleFavorite(reference) }
            )
        }
        composable("audio") { AudioScreen(onBack = { nav.popBackStack() }) }
        composable("search") {
            SearchScreen(onBack = { nav.popBackStack() }, onSelectBook = { book ->
                selectedBook = book
                selectedChapter = 1
                nav.navigate("reader")
            })
        }
        composable("favorites") {
            FavoritesScreen(favorites, onBack = { nav.popBackStack() }, onOpen = { name, chapter ->
                selectedBook = bibleBooks.find { it.name == name } ?: bibleBooks.first()
                selectedChapter = chapter.coerceIn(1, selectedBook.chapters)
                nav.navigate("reader")
            })
        }
    }
}
