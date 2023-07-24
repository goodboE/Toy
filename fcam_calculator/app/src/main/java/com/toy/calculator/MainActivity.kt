package com.toy.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.toy.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val firstNumberStringBuilder = StringBuilder("")
    private val secondNumberStringBuilder = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @SuppressLint("SetTextI18n")
    fun numberClicked(view: View) {
        val numberString = (view as? Button)?.text?.toString() ?: ""
        val numberStringBuilder = if (operatorText.isEmpty()) firstNumberStringBuilder else secondNumberStringBuilder

        numberStringBuilder.append(numberString)
        updateEquationTextView()
    }

    fun clearClicked(view: View) {
        firstNumberStringBuilder.clear()
        secondNumberStringBuilder.clear()
        operatorText.clear()
        updateEquationTextView()
        binding.resultTextView.text = ""
    }

    fun equalClicked(view: View) {

        if (firstNumberStringBuilder.isEmpty() || secondNumberStringBuilder.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "잘못된 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operatorText.toString()) {
            "+" -> decimalFormat.format(
                firstNumberStringBuilder.toString().toBigDecimal() + secondNumberStringBuilder.toString().toBigDecimal()
            )
            "-" -> decimalFormat.format(
                firstNumberStringBuilder.toString().toBigDecimal() - secondNumberStringBuilder.toString().toBigDecimal()
            )
            else -> return
        }

        binding.resultTextView.text = result

    }

    fun operatorClicked(view: View) {
        val operatorString = (view as? Button)?.text?.toString() ?: ""

        if (firstNumberStringBuilder.isEmpty()) {
            Toast.makeText(this, "숫자를 먼저 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (secondNumberStringBuilder.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }


        operatorText.append(operatorString)
        updateEquationTextView()
    }

    @SuppressLint("SetTextI18n")
    private fun updateEquationTextView() {

        val firstFormattedNumber = if (firstNumberStringBuilder.isNotEmpty()) decimalFormat.format(firstNumberStringBuilder.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if (secondNumberStringBuilder.isNotEmpty()) decimalFormat.format(secondNumberStringBuilder.toString().toBigDecimal()) else ""

        binding.equationTextView.text = "$firstFormattedNumber $operatorText $secondFormattedNumber"
    }

}