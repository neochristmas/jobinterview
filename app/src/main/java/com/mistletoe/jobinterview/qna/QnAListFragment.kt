package com.mistletoe.jobinterview.qna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mistletoe.jobinterview.data.QnA
import com.mistletoe.jobinterview.databinding.FragmentQnalistBinding
import com.mistletoe.jobinterview.ui.AddActivity

class QnAListFragment : Fragment(), QnAListAdapter.AddItemClickListener {

    private lateinit var binding: FragmentQnalistBinding
    private lateinit var adapter: QnAListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQnalistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    /*
        TODO DB에서 데이터 호출해 셋팅.
     */
    private fun setData() {
        val parentList = listOf("Tell me about yourself", "Android")
        val childList = hashMapOf(
            "Tell me about yourself" to listOf(
                QnA(
                    tag = "myself",
                    question = "What is your name?",
                    answer = "My name is ..."
                )
            ),
            "Android" to listOf(
                QnA(
                    tag = "android",
                    question = "What is android?",
                    answer = "Android is an open-source mobile operating system developed by Google, based on the Linux kernel. It is designed for touchscreen devices and provides a flexible framework for developers to build applications using Kotlin, Java, or C++."
                ),
                QnA(tag = "android", question = "What is binding?", answer = "Binding is ..."),
            )
        )

        adapter = QnAListAdapter(requireContext(), parentList, childList, binding, this)
        binding.expandCategory.setAdapter(adapter)

    }

    override fun moveAddScreen() {
        Log.d("IJ", "Move to Add Screen...")
        val intent = Intent(requireContext(), AddActivity::class.java)
        startActivity(intent)
    }
}