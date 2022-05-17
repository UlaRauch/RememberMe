package com.example.rememberme.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.rememberme.models.Reminder
//import com.example.rememberme.models.getReminders
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.viewmodels.RememberViewModel
import com.example.rememberme.widgets.RememberRow

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: RememberViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "RememberMe") },
            )
        }
    ) {
        //viewModel.addReminder(Reminder(title = "next", day = 1, month = 1, year = 2023, hour = 22, minute = 0, text = "hello"))
       //Log.i("HomeScreen", "reminder added")
        val reminders: List<Reminder> by viewModel.reminders.collectAsState()
        Log.i("HomeScreen", "get reminders")
        MainContent(
            navController = navController,
            reminders = reminders
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    reminders: List<Reminder>
) {
    LazyColumn() {
        items(reminders) { reminder ->
            RememberRow(
                reminder = reminder,
            )
            { reminderID ->
                //navController.navigate("HomeScreen")
                if (reminderID != null) {
                    navController.navigate(RememberScreens.DetailScreen.name + "/$reminderID")
                    Log.d("Navigation", "Reminder clicked. ID: ${reminder.id}")
                } else Log.d("Navigation", "No ID")
            }

            /*
            Card() {
                Column() {
                    Text(text = reminder.title)
                    Text(text = reminder.text)
                }
            }

             */
        }

    }
}