package com.toy.blog_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toy.blog_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }


    private fun initRecyclerView() {
        val dummyList = mutableListOf<Pokemon>(
            Pokemon("파이리", "10", "불"),
            Pokemon("파이리", "10", "불"),
            Pokemon("파이리", "10", "불"),
            Pokemon("파이리", "10", "불"),
            Pokemon("파이리", "10", "불"),
            Pokemon("파이리", "10", "불"),
            Pokemon("파이리", "10", "불"),
        )

        binding.pokeRecyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = PokeAdapter(dummyList)
            val decoration = DividerItemDecoration(applicationContext, RecyclerView.VERTICAL)
            addItemDecoration(decoration)
        }
    }
}