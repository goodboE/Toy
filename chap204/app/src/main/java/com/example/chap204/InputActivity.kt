package com.example.chap204

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.chap204.databinding.ActivityInputBinding


class InputActivity : AppCompatActivity() {

    private val binding: ActivityInputBinding by lazy {
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

        binding.saveButton.setOnClickListener {
            with(getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()) {
                putString(NAME, binding.nameValueEditText.text.toString())
                putString(BIRTH_DATE, binding.birthValueTextView.text.toString())
                putString(BLOOD_TYPE, getBloodType())
                putString(PHONE_NUMBER, binding.phoneValueEditText.text.toString())
                putString(CAUTION, getCautionMessage())
                apply()
            }

            Toast.makeText(this, "유저 정보를 저장했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }


    }
    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodEditSpinner.selectedItem.toString()
        val bloodSign = if (binding.plusRadioButton.isChecked) "RH+" else "RH-"
        return "$bloodSign $bloodAlphabet"
    }

    private fun getCautionMessage(): String {
        return if (binding.cautionCheckBox.isChecked) binding.cautionValueEditText.text.toString() else ""
    }


}