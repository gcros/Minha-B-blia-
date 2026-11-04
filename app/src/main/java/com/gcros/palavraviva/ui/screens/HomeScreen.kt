package com.gcros.palavraviva.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gcros.palavraviva.ui.theme.*

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PergaminhoClaro)
            .padding(horizontal = 22.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "BÍBLIA SAGRADA",
                    color = Cafe,
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Palavra Viva",
                    color = MarromProfundo,
                    fontSize = 29.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = { /* TODO: busca */ }) {
                Icon(Icons.Outlined.Search, "Buscar", tint = MarromProfundo)
            }
            IconButton(onClick = { /* TODO: favoritos */ }) {
                Icon(Icons.Outlined.Bookmark, "Favoritos", tint = MarromProfundo)
            }
        }

        Spacer(Modifier.size(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Pergaminho)
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.WbSunny, null, tint = DouradoEnvelhecido)
                    Text(
                        "  PALAVRA DO DIA",
                        color = Cafe,
                        fontSize = 11.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.size(16.dp))
                Text(
                    "“O Senhor é meu pastor, nada me faltará.”",
                    color = MarromProfundo,
                    fontSize = 23.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily.Serif
                )
                Spacer(Modifier.size(10.dp))
                Text("Salmo 23, 1", color = Cafe, fontSize = 14.sp)
            }
        }

        Spacer(Modifier.size(26.dp))

        Text(
            "Alimente sua alma",
            color = MarromProfundo,
            fontSize = 21.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Reserve um momento com a Palavra de Deus.",
            color = Cafe,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 5.dp)
        )

        Spacer(Modifier.size(18.dp))

        Button(
            onClick = { /* TODO: abrir livros */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Outlined.MenuBook, null)
            Text("  Abrir a Bíblia", modifier = Modifier.padding(vertical = 8.dp))
        }

        OutlinedButton(
            onClick = { /* TODO: abrir áudio */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Outlined.Headphones, null)
            Text("  Ouvir a Palavra")
        }

        Spacer(Modifier.size(20.dp))

        Text(
            "“Lâmpada para os meus pés é tua palavra,
e luz para o meu caminho.”",
            color = Cafe,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily.Serif
        )
        Text(
            "Salmo 119, 105",
            color = DouradoEnvelhecido,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(Modifier.weight(1f))
        Text(
            "FEITO COM FÉ  ✦  AD MAIOREM DEI GLORIAM",
            color = DouradoEnvelhecido,
            fontSize = 10.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )
    }
}
