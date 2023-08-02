package com.toy.fishshapedbread

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.toy.fishshapedbread.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val fishes = mutableListOf(
            Fish("1", "1", false),
            Fish("2", "2", false),
            Fish("3", "3", false),
            Fish("4", "4", false),
            Fish("5", "5", false),
            Fish("6", "6", false),
            Fish("7", "7", false),
            Fish("8", "8", false),
            Fish("9", "9", false),
            Fish("10", "10", false),
            Fish("99", "99", false),
        )


        binding.fishRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = FishAdapter(fishes)
            val decoration = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
        }

        binding.button.setOnClickListener {
            (binding.fishRecyclerView.adapter as? FishAdapter)?.allClick()
        }
    }
}