package com.mistletoe.jobinterview.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mistletoe.jobinterview.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
    }

    private fun setupUi() {
        binding.apply {
            buttonSave.setOnClickListener {
                Log.d("Add Screen", "Click save button...")
                viewModel.createQnA(
                    tag = "android",
                    question = editQuestion.text.toString(),
                    answer = editAnswer.text.toString(),
                )
                finish()
            }

            buttonCancel.setOnClickListener {
                Log.d("Add Screen", "Click cancel button...")
                finish()
            }
        }

    }

}