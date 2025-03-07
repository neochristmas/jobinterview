package com.mistletoe.jobinterview.ui

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.data.QnA
import com.mistletoe.jobinterview.databinding.ActivityPracticeBinding
import java.util.Locale

class PracticeActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityPracticeBinding
    private lateinit var qnaList: ArrayList<QnA>
    private lateinit var tts: TextToSpeech
    private var currentIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)

        qnaList = intent.getParcelableArrayListExtra("qna_list") ?: arrayListOf()

        updateUI()

        // 왼쪽(이전) 버튼 클릭 시
        binding.buttonPrev.setOnClickListener {
            if (currentIdx > 0) {
                currentIdx--
                updateUI()
            }
        }

        // 오른쪽(다음) 버튼 클릭 시
        binding.buttonNext.setOnClickListener {
            if (currentIdx < qnaList.size - 1) {
                currentIdx++
                updateUI()
            }
        }


        binding.buttonPlay.setOnClickListener {
            speakOut()
        }

        binding.buttonComplete.setBackgroundColor(getResources().getColor(R.color.sky_blue))
        binding.buttonComplete.setOnClickListener {
            finish()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                binding.buttonPlay.isEnabled = false
                binding.textQuestion.text = "TTS Not Supported"
            }
        }
    }

    private fun speakOut() {
        val text = binding.textQuestion.text.toString()
        if (text.isNotEmpty()) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onDestroy() {
        // TTS 리소스 해제
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }

    private fun updateUI() {
        val currentQnA = qnaList[currentIdx]
        binding.textQuestion.text = currentQnA.question
        binding.textAnswer.text = currentQnA.answer
        binding.textCurrentIndex.text =
            getString(R.string.practice_current_index, currentIdx + 1, qnaList.size)

        // 첫 번째 아이템이면 이전 버튼 비활성화
        binding.buttonPrev.isEnabled = currentIdx > 0

        // 마지막 아이템이면 다음 버튼 비활성화
        binding.buttonNext.isEnabled = currentIdx < qnaList.size - 1
    }
}