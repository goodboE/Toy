package com.example.chap203

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.chap203.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var inputNumber: Int = 0
    private var cmToM: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.inputEditText.addTextChangedListener { text ->
            inputNumber = if (text.isNullOrEmpty()) {
                0
            } else {
                text.toString().toInt()
            }

            if (cmToM) {
                mBinding.outputTextView.text = inputNumber.times(0.01).toString()
            } else {
                mBinding.outputTextView.text = inputNumber.times(100).toString()
            }

        }

        mBinding.swapButton.setOnClickListener {
            cmToM = cmToM.not()

            if (cmToM) {
                mBinding.cmTextView.text = "cm"
                mBinding.mTextView.text = "m"
                mBinding.outputTextView.text = inputNumber.times(0.01).toString()
            } else {
                mBinding.cmTextView.text = "m"
                mBinding.mTextView.text = "cm"
                mBinding.outputTextView.text = inputNumber.times(100).toString()
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("cmToM", cmToM)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmToM = savedInstanceState.getBoolean("cmToM")
        mBinding.cmTextView.text = if (cmToM) "cm" else "m"
        mBinding.mTextView.text = if (cmToM) "m" else "cm"
        super.onRestoreInstanceState(savedInstanceState)
    }


}