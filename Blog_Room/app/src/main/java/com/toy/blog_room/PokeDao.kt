package com.toy.blog_room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PokeDao {

    @Query("SELECT * FROM pokemon ORDER BY id DESC")
    fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokemon ORDER BY id DESC LIMIT 1")
    fun getLatestPokemon(): Pokemon

    @Insert
    fun insert(pokemon: Pokemon)

    @Delete
    fun delete(pokemon: Pokemon)

    @Update
    fun update(pokemon: Pokemon)

}