package com.example.chap204

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.chap204.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)


        mBinding.goInputButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        mBinding.deleteButton.setOnClickListener {
            deleteData()
        }
        mBinding.callLayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = mBinding.phoneValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getUiAndData()
    }

    private fun getUiAndData() {
        with(getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)) {
            mBinding.nameValueTextView.text = getString(NAME, "미정")
            mBinding.birthValueTextView.text = getString(BIRTH_DATE, "미정")
            mBinding.bloodValueTextView.text = getString(BLOOD_TYPE, "미정")
            mBinding.phoneValueTextView.text = getString(PHONE_NUMBER, "미정")

            val cautionValue = getString(CAUTION, "")
            if (cautionValue.isNullOrEmpty()) {
                mBinding.cautionTextView.isVisible = false
                mBinding.cautionValueTextView.isVisible = false
            } else {
                mBinding.cautionTextView.isVisible = true
                mBinding.cautionValueTextView.isVisible = true
                mBinding.cautionValueTextView.text = cautionValue
            }


        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()) {
            clear()
            apply()
            getUiAndData()
        }
        Toast.makeText(this, "유저 데이터를 삭제했습니다", Toast.LENGTH_SHORT).show()
    }



}