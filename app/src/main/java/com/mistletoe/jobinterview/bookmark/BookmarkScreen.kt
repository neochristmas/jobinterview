package com.mistletoe.jobinterview.bookmark

import android.util.Log
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.data.model.QnA

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
) {
    // ViewModel 에서 Flow -> Compose State 변환
    val qnaList by viewModel.bookmarkList.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
        ) {
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
    Text(
        text = stringResource(R.string.bookmark_list),
        fontSize = 24.sp,
        color = Color.Black,
        fontFamily = FontFamily.SansSerif
    )
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
            imageVector = if (qna.isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    Log.d("BookmarkItem", "Icon clicked for qnaId: ${qna.isBookmarked}")
                    onBookmarkClick(qna.copy(isBookmarked = !qna.isBookmarked))
                }
                .padding(start = 8.dp),
            tint = Color.Blue
        )
    }
}