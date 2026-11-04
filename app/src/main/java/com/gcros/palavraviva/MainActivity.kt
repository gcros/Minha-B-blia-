package com.gcros.palavraviva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gcros.palavraviva.ui.screens.HomeScreen
import com.gcros.palavraviva.ui.theme.PalavraVivaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PalavraVivaTheme {
                HomeScreen()
            }
        }
    }
}
