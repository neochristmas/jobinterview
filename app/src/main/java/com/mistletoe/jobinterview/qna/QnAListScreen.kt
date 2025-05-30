package com.mistletoe.jobinterview.qna

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun QnAListScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TitleText()
        }
    }
}

@Composable
fun TitleText() {
    Text("QnA list by category")
}