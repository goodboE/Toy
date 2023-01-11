package com.example.chap204

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.chap204.databinding.ActivityInputBinding


class InputActivity : AppCompatActivity() {

    val binding: ActivityInputBinding by lazy {
        ActivityInputBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bloodEditSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.editDateLayer.setOnClickListener {

            val listener = OnDateSetListener { _, year, month, day ->
                binding.birthValueTextView.text = "$year-${month+1}-$day"
            }

            DatePickerDialog(
                this,
                listener,
                2000,
                1,
                1
            ).show()
        }

        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.cautionValueEditText.isVisible = isChecked
        }
        binding.cautionValueEditText.isVisible = binding.cautionCheckBox.isChecked


    }
}