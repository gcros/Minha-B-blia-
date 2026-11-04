package com.gcros.palavraviva.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.gcros.palavraviva.ui.screens.*

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    var selectedBook by remember { mutableStateOf(bibleBooks.first()) }
    var selectedChapter by remember { mutableIntStateOf(1) }

    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onOpenBible = { nav.navigate("books") },
                onListen = { nav.navigate("audio") }
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
            ReaderScreen(selectedBook, selectedChapter, onBack = { nav.popBackStack() }, onChapterChange = { selectedChapter = it })
        }
        composable("audio") { AudioScreen(onBack = { nav.popBackStack() }) }
    }
}
