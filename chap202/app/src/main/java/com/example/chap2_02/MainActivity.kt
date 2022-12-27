package com.example.chap2_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chap2_02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var startNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        initButtons()

    }

    private fun initButtons() {

        mBinding.resetButton.setOnClickListener {
            startNumber = 0
            setNumberText()
            Log.d("onClick", "reset!! number=$startNumber")
        }

        mBinding.plusButton.setOnClickListener {
            startNumber+=1
            setNumberText()
            Log.d("onClick", "plus!! number=$startNumber")
        }
    }

    private fun setNumberText() {
        mBinding.numberTextView.text = startNumber.toString()
    }


}