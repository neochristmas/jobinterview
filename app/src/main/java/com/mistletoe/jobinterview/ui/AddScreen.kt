package com.mistletoe.jobinterview.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun AddScreen(
    navController: NavHostController,
    category: String,
    viewModel: AddViewModel = hiltViewModel(),
) {
    var questionInput by remember { mutableStateOf("") }
    var answerInput by remember { mutableStateOf("") }

    val isBothFilled = questionInput.isNotBlank() && answerInput.isNotBlank()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (TopAppBar(
                title = { Text("Add") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            ))
        },
        bottomBar = {
            BottomBarButtons(navController, enabled = isBothFilled, onClick = {
                Log.d("Input Test...", "$questionInput, $answerInput")
                viewModel.createQnA(
                    tag = category,
                    question = questionInput,
                    answer = answerInput,
                )
                navController.popBackStack()
            })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text("Category: $category")
            QuestionInput(value = questionInput, onValueChange = {
                questionInput = it
            })
            AnswerInput(value = answerInput, onValueChange = {
                answerInput = it
            })
        }
    }
}

@Composable
private fun BottomBarButtons(
    navController: NavHostController,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = if (enabled) Color.Blue else Color.LightGray
            )
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(0.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text("CANCEL", color = Color.Blue)
            }
        }

        Button(
            onClick = onClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(0.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text("SAVE", color = Color.White)
        }
    }
}

@Composable
fun QuestionInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Question") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun AnswerInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Answer") },
        maxLines = 10,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
    )
}

