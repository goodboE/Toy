package com.toy.fcam_voca

import androidx.room.*

@Dao
interface WordDao {
    @Query("SELECT * FROM word ORDER BY id DESC")
    fun getAll(): List<WordModel>

    @Query("SELECT * FROM word ORDER BY id DESC LIMIT 1")
    fun getLatestWord(): WordModel

    @Insert
    fun insert(word: WordModel)

    @Delete
    fun delete(word: WordModel)

    @Update
    fun update(word: WordModel)
}