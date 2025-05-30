package com.mistletoe.jobinterview.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mistletoe.jobinterview.data.model.QnA

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = viewModel(),
) {
    // ViewModel에서 Flow -> Compose State 변환
    val qnaList by viewModel.bookmarkedQnAs.collectAsState()

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TitleText()
            LazyColumn {
                items(qnaList,
                    key = { it.qnaId }) { qna ->
                    BookmarkItem(
                        qna = qna,
                        onBookmarkClick = { updatedQnA ->
                            viewModel.updateQnA(updatedQnA)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TitleText() {
    Text("Bookmark List")
}

@Composable
fun BookmarkItem(qna: QnA, onBookmarkClick: (QnA) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Text(text = "Q. ")
                Text(text = qna.question)
            }
            Row {
                Text(text = "A. ")
                Text(text = qna.answer)
            }
        }
        Icon(
            Icons.Default.Favorite, contentDescription = null,
            modifier = Modifier
                .clickable { onBookmarkClick(qna.copy(isBookmarked = !qna.isBookmarked)) }
                .padding(start = 8.dp),
            tint = Color.Blue
        )
    }
}