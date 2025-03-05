package com.mistletoe.jobinterview.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.bookmark.BookmarkFragment
import com.mistletoe.jobinterview.databinding.ActivityMainBinding
import com.mistletoe.jobinterview.qna.QnAListFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            bottomNav.setOnItemSelectedListener(this@MainActivity)
        }
    }

    private fun onQnaListClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, QnAListFragment())
        }
        return true
    }

    private fun onBookmarkClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, BookmarkFragment())
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_list -> onQnaListClicked()
        R.id.nav_bookmark -> onBookmarkClicked()
        else -> false
    }

}
