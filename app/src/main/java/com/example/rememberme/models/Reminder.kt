package com.example.rememberme.models

data class Reminder(
    val id: String,
    val title: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val hour: Int,
    val minute: Int,
    val text: String
)

fun getReminders(): List<Reminder> {
    return listOf(
        Reminder(
            id = "1",
            title = "My first reminder",
            day = 9,
            month = 5,
            year = 2022,
            hour = 12,
            minute = 0,
            text = "Brush your teeth!"
        ),
        Reminder(
            id = "2",
            title = "My second reminder",
            day = 9,
            month = 5,
            year = 2022,
            hour = 12,
            minute = 0,
            text = "Floss like a boss!"
        )
    )
}
