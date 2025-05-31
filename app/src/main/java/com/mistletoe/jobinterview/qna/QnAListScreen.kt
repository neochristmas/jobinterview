package com.mistletoe.jobinterview.qna

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.bookmark.BookmarkItem

@Composable
fun QnAListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // NestedScrollView 대체
            .padding(20.dp)
    ) {
        TitleText()
        QnaExpandableList()
    }

}

@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.qna_title),
        fontSize = 24.sp,
        color = Color.Black,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun QnaExpandableList(viewModel: QnAListViewModel = viewModel()) {
    val qnaList by viewModel.qnaList.collectAsState()

    // 카테고리 별 필터링
    val parentList = listOf("Tell me about yourself", "Android")
    val childMap = parentList.associateWith { category ->
        qnaList.filter { it.tag == category }
    }

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    Column { // 1. 최상위 Column → 화면 전체 리스트를 수직으로 배치
        childMap.forEach { (category, qaList) ->
            val expanded = expandedStates[category] ?: false

            Column( // 2. 각 카테고리 묶음 → 카테고리 제목 + QA 리스트
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = category, fontSize = 18.sp, color = Color.DarkGray)
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                expandedStates[category] = !expanded
                            }
                    )
                }
                if (expanded) {
                    qaList.forEach { qna ->
                        Column(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 4.dp,
                            )
                        ) { // 3. 하나의 QA → 질문과 답을 세로로 배치
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
    }
}
