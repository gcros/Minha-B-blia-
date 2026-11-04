package com.gcros.palavraviva.data

import android.content.Context
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Simple local favorites store. No cloud account is required.
 * Persisted as chapter references: "Book|chapter".
 */
class PreferencesRepository(context: Context) {
    private val prefs = context.getSharedPreferences("palavra_viva", Context.MODE_PRIVATE)
    private val _favorites = MutableStateFlow(prefs.getStringSet("favorites", emptySet()) ?: emptySet())
    val favorites: StateFlow<Set<String>> = _favorites

    fun toggleFavorite(reference: String) {
        val next = _favorites.value.toMutableSet()
        if (!next.add(reference)) next.remove(reference)
        _favorites.value = next
        prefs.edit().putStringSet("favorites", next.toSet()).apply()
    }

    fun isFavorite(reference: String) = reference in _favorites.value
}
