package com.toy.blog_room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(
    val name: String,
    val level: String,
    val type: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
