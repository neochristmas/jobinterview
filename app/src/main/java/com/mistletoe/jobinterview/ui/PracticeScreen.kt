package com.mistletoe.jobinterview.ui

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mistletoe.jobinterview.data.model.QnA
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

    val currentQnA = qnaList[currentIdx]

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Practice") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            PracticeBottomBar(
                currentIdx = currentIdx,
                qnaListSize = qnaList.size,
                onPrev = {
                    if (currentIdx > 0) currentIdx--
                    isAnswerHidden = true
                },
                onPlayTTS = {
                    tts.speak(currentQnA.question, TextToSpeech.QUEUE_FLUSH, null, null)
                },
                onNext = {
                    if (currentIdx < qnaList.size - 1) currentIdx++
                    isAnswerHidden = true
                }
            )
        }
    ) { innerPadding ->
        if (qnaList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No questions available.")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .background(Color.White)
        ) {
            // QnA Card
            QnACard(currentQnA = currentQnA,
                isAnswerHidden = isAnswerHidden,
                currentIdx = currentIdx,
                qnaListSize = qnaList.size,
                onToggleAnswer = {
                    isAnswerHidden = !isAnswerHidden
                })

            Spacer(modifier = Modifier.height(20.dp))

            // Navigation Row
//            NavigationRow(
//                currentIdx = currentIdx,
//                qnaListSize = qnaList.size,
//                onPrev = {
//                    if (currentIdx > 0) currentIdx--
//                    isAnswerHidden = true
//                },
//                onNext = {
//                    if (currentIdx < qnaList.size - 1) currentIdx++
//                    isAnswerHidden = true
//                },
//            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action Row
//            ActionRow(
//                tts = tts,
//                currentIdx = currentIdx,
//                lastIdx = qnaList.lastIndex,
//                onPlayTTS = {
//                    tts.speak(currentQnA.question, TextToSpeech.QUEUE_FLUSH, null, null)
//                },
//                onGoToLast = {
//                    currentIdx = qnaList.lastIndex
//                    isAnswerHidden = true
//                },
//                onFinish = {
//                    navHostController.popBackStack()
//                }
//            )
        }
    }


}

@Composable
fun ActionRow(
    tts: TextToSpeech,
    currentIdx: Int,
    lastIdx: Int,
    onPlayTTS: () -> Unit,
    onGoToLast: () -> Unit,
    onFinish: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(
            onClick = onPlayTTS,
            enabled = tts.language != null
        ) {
            Text("Play TTS")
        }

        Button(
            onClick = onGoToLast,
            enabled = currentIdx != lastIdx
        ) {
            Text("Go to Last")
        }

//        Button(
//            onClick = onFinish,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Finish")
//        }
    }
}

@Composable
fun NavigationRow(
    currentIdx: Int,
    qnaListSize: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPrev,
            enabled = currentIdx > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text("Prev")
        }

        Text(
            text = "${currentIdx + 1} / $qnaListSize",
            style = MaterialTheme.typography.bodySmall
        )

        Button(
            onClick = onNext,
            enabled = currentIdx < qnaListSize - 1,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text("Next")
        }
    }
}

@Composable
fun QnACard(
    currentQnA: QnA,
    isAnswerHidden: Boolean,
    currentIdx: Int,
    qnaListSize: Int,
    onToggleAnswer: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight * 2 / 3

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .border(1.dp, Color(0xFFDC613C), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "Q. ${currentQnA.question}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (isAnswerHidden) {
                    Text(
                        text = "Tap to show answer",
                        modifier = Modifier
                            .clickable { onToggleAnswer() }
                    )
                } else {
                    Text(
                        text = "A. ${currentQnA.answer}",
                        modifier = Modifier
                            .clickable { onToggleAnswer() }
                    )
                }
            }

            Text(
                text = "${currentIdx + 1} / $qnaListSize",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun PracticeBottomBar(
    currentIdx: Int,
    qnaListSize: Int,
    onPrev: () -> Unit,
    onPlayTTS: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPrev,
            enabled = currentIdx > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(0.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text("PREV")
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
        ) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = Color.LightGray
            )
            IconButton(
                onClick = onPlayTTS,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play TTS",
                    tint = Color.Blue
                )
            }
        }

        Button(
            onClick = onNext,
            enabled = currentIdx < qnaListSize - 1,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(0.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text("NEXT")
        }
    }


}

