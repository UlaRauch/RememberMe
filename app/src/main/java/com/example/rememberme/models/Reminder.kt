package com.example.rememberme.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String?,
    val day: Int,
    val month: Int,
    val year: Int,
    val hour: Int = 0,
    val minute: Int = 0,
    val text: String?
    )
