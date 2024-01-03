package com.example.example_mvc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.example_mvc.databinding.ActivityMainBinding
import com.example.example_mvc.mvc.MvcActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }

    }

    fun openMVC() {
        startActivity(Intent(this, MvcActivity::class.java))
    }

    fun openMVP() {

    }

    fun openMVVM() {

    }

    fun openMVI() {

    }
}