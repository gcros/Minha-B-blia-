package com.gcros.palavraviva.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(onSelectBook: (BibleBook) -> Unit, onBack: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val results = bibleBooks.filter { it.name.contains(query.trim(), ignoreCase = true) }
    Scaffold(topBar = {
        TopAppBar(title = { Text("Buscar na Bíblia") }, navigationIcon = {
            TextButton(onClick = onBack) { Text("‹ Voltar") }
        })
    }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = query, onValueChange = { query = it },
                label = { Text("Nome do livro") }, singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(results) { book ->
                    ListItem(headlineContent = { Text(book.name) }, supportingContent = { Text("${book.chapters} capítulos") },
                        modifier = Modifier.clickable { onSelectBook(book) })
                }
            }
        }
    }
}
