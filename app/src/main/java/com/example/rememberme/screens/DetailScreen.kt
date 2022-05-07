package com.example.rememberme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rememberme.models.Reminder
import com.example.rememberme.models.getReminders

@Composable
fun DetailScreen() {
    val reminder= reminderFilter(reminderId = "1")
    Scaffold(
        topBar = {
            TopAppBar(){
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                        }
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = reminder.title)

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.clickable {
                        }
                    )
                }
            }
        }) {
        MainContentD(
            reminder = reminder
        )
    }
}

@Composable
fun MainContentD(reminder:Reminder) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column() {
            Row() {
                Text(text = "${reminder.day}.")
                Text(text = "${reminder.month}.")
                Text(text = "${reminder.year} ")
                Text(text = "${reminder.hour}:")
                Text(text = "${reminder.minute}")
            }
            Text(text = reminder.text)
        }
    }
}


fun reminderFilter(reminderId: String?): Reminder {
    return getReminders().filter { reminder -> reminder.id == reminderId }[0]
}