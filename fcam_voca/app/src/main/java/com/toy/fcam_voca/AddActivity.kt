package com.toy.fcam_voca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.toy.fcam_voca.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var originWord: WordModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.addButton.setOnClickListener {
            if (originWord == null) add()
            else edit()
        }
    }

    private fun initViews() {
        val wordTypes = listOf(
            "명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사",
        )
        binding.typeChipGroup.apply {
            wordTypes.forEach { wordType ->
                addView(createChip(wordType))
            }
        }

        originWord = intent.getParcelableExtra<WordModel>("originWord")
        originWord?.let { word ->
            binding.wordInputEditText.setText(word.word)
            binding.meanInputEditText.setText(word.mean)
            val selectedChip = binding.typeChipGroup.children.firstOrNull { (it as Chip).text == word.type } as? Chip
            selectedChip?.isChecked = true
        }
    }

    private fun createChip(title: String): Chip {
        return Chip(this).apply {
            text = title
            isCheckable = true
            isClickable = true
        }
    }

    private fun add() {

        val word = binding.wordInputEditText.text.toString()
        val mean = binding.meanInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val wordModel = WordModel(word, mean, type)

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.insert(wordModel)
            runOnUiThread {
                Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent().putExtra("UPDATED", true)
            setResult(RESULT_OK, intent)
            finish()
        }.start()
    }

    private fun edit() {
        val word = binding.wordInputEditText.text.toString()
        val mean = binding.meanInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val editedWord = originWord?.copy(word = word, mean = mean, type = type)

        Thread {
            editedWord?.let {
                AppDatabase.getInstance(this)?.wordDao()?.update(it)
                runOnUiThread {
                    Toast.makeText(this, "edit success", Toast.LENGTH_SHORT).show()
                }
            }
            val intent = Intent().putExtra("editWord", editedWord)
            setResult(RESULT_OK, intent)
            finish()
        }.start()
    }
}