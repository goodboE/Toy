package com.toy.fcam_voca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.toy.fcam_voca.databinding.ActivityMainBinding
import java.nio.file.Files.delete

class MainActivity : AppCompatActivity(), WordAdapter.WordClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: WordModel? = null
    private val updatedAddWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isUpdated = result.data?.getBooleanExtra("UPDATED", false) ?: false

        if (isUpdated && result.resultCode == RESULT_OK) {
            updateAddWord()
        }
    }

    private val updateEditWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val editWord = result.data?.getParcelableExtra<WordModel>("editWord")

        if (editWord != null && result.resultCode == RESULT_OK) {
            updateEditWord(editWord)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                updatedAddWordResult.launch(it)
            }
        }

        binding.deleteImageView.setOnClickListener {
            delete()
        }
        binding.editImageView.setOnClickListener {
            edit()
        }

    }

    private fun initRecyclerView() {

        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.wordRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = wordAdapter
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        // fetch database
        Thread {
            val wordList = AppDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.list.addAll(wordList)
            runOnUiThread {
                wordAdapter.notifyDataSetChanged()
            }

        }.start()

    }

    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { latestWord ->
                wordAdapter.list.add(0, latestWord)
                runOnUiThread { wordAdapter.notifyDataSetChanged() }
            }

        }.start()
    }

    private fun updateEditWord(word: WordModel) {
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list.set(index, word)
        runOnUiThread {
            selectedWord = word
            wordAdapter.notifyItemChanged(index)
            binding.wordTextView.text = word.word
            binding.meanTextView.text = word.mean
        }
    }

    private fun delete() {
        if (selectedWord == null) return

        Thread {
            selectedWord?.let { selectedWord ->
                AppDatabase.getInstance(this)?.wordDao()?.delete(selectedWord)
                runOnUiThread {
                    wordAdapter.list.remove(selectedWord)
                    wordAdapter.notifyDataSetChanged()
                    binding.wordTextView.text = ""
                    binding.meanTextView.text = ""
                    Toast.makeText(this, "delete success!", Toast.LENGTH_SHORT).show()
                }
            }
            selectedWord = null
        }.start()
    }

    private fun edit() {
        if (selectedWord == null) return

        val intent = Intent(this, AddActivity::class.java).putExtra("originWord", selectedWord)
        updateEditWordResult.launch(intent)

    }


    override fun onClick(word: WordModel) {
        selectedWord = word

        // ui update
        binding.wordTextView.text = word.word
        binding.meanTextView.text = word.mean
    }
}