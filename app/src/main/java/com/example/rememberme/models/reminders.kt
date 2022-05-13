package com.example.rememberme.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class reminders(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val text: String
)
