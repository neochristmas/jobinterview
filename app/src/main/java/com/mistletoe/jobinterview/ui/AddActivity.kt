package com.mistletoe.jobinterview.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var category: String
    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category")!!
        setupUi()
    }

    private fun setupUi() {
        binding.apply {

            // 카테고리 표시
            textCategory.text = getString(R.string.add_category, category)

            // 입력값 유효성 검사
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    val questionText = editQuestion.text.toString().trim()
                    val answerText = editAnswer.text.toString().trim()

                    val isBothFilled = questionText.isNotEmpty() && answerText.isNotEmpty()
                    buttonSave.isEnabled = isBothFilled

                    if (isBothFilled) {
                        buttonSave.setBackgroundColor(getResources().getColor(R.color.sky_blue))
                    } else {
                        buttonSave.setBackgroundColor(getResources().getColor(R.color.grey))
                    }
                }

            }

            editQuestion.addTextChangedListener(textWatcher)
            editAnswer.addTextChangedListener(textWatcher)

            buttonSave.setOnClickListener {
                Log.d("Add Screen", "Click save button...")
                viewModel.createQnA(
                    tag = category,
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