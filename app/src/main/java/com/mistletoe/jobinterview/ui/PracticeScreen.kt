package com.mistletoe.jobinterview.ui

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mistletoe.jobinterview.qna.QnAListViewModel
import java.util.Locale

@Composable
fun PracticeScreen(
    navHostController: NavHostController,
    viewModel: QnAListViewModel
) {
    val context = LocalContext.current
    var currentIdx by remember { mutableStateOf(0) }
    var isAnswerHidden by remember { mutableStateOf(true) }
    val qnaList = viewModel.getQnAList()

    Log.d("QnAList...", qnaList.toString())

    val tts = remember(context) {
        TextToSpeech(context) {}
    }

    DisposableEffect(Unit) {
        val result = tts.setLanguage(Locale.UK)

        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    if (qnaList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No questions available.")
        }
        return
    }

    val currentQnA = qnaList[currentIdx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Q: ${currentQnA.question}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isAnswerHidden) {
            Text(
                text = "Tap to show answer",
                modifier = Modifier
                    .clickable { isAnswerHidden = false }
                    .padding(8.dp)
            )
        } else {
            Text(
                text = "A: ${currentQnA.answer}",
                modifier = Modifier
                    .clickable { isAnswerHidden = true }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${currentIdx + 1} / ${qnaList.size}",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (currentIdx > 0) currentIdx--
                    isAnswerHidden = true
                },
                enabled = currentIdx > 0
            ) {
                Text("Prev")
            }

            Button(
                onClick = {
                    if (currentIdx < qnaList.size - 1) currentIdx++
                    isAnswerHidden = true
                },
                enabled = currentIdx < qnaList.size - 1
            ) {
                Text("Next")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                tts.speak(currentQnA.question, TextToSpeech.QUEUE_FLUSH, null, null)
            },
            enabled = tts.language != null
        ) {
            Text("Play TTS")
        }

        Button(
            onClick = {
                currentIdx = qnaList.lastIndex
                isAnswerHidden = true
            }
        ) {
            Text("Go to Last")
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish")
        }
    }
}
