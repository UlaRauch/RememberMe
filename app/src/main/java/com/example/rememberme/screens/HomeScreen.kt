package com.example.rememberme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rememberme.models.Reminder
import com.example.rememberme.models.getReminders

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "RememberMe") },
            )
        }
    ) {
        MainContent(
        )
    }
}

@Composable
fun MainContent(reminders: List<Reminder> = getReminders()) {
    LazyColumn() {
        items(reminders) { reminder ->
            Card() {
                Column() {
                    Text(text = reminder.title)
                    Text(text = reminder.text)
                }
            }
        }

    }
}