package com.example.githubrepo

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest01 {

    @Test
    fun test01() = runBlocking {
        val time = measureTimeMillis {
            val name = getFirstName()
            val lastName = getLastName()
            println("Hello, $name $lastName")
        }
        println("measured time : $time")
    }

    @Test
    fun test02() = runBlocking {
        val time = measureTimeMillis {
            val name = async { getFirstName() }
            val lastName = async { getLastName() }
            println("Hello, ${name.await()} ${lastName.await()}")
        }
        println("measured time : $time")
    }



    private suspend fun getFirstName(): String {
        delay(1000)
        return "Ko"
    }

    private suspend fun getLastName(): String {
        delay(1000)
        return "Hyeon Jin"
    }
}