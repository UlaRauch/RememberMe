package com.example.rememberme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rememberme.models.Reminder
//import com.example.rememberme.models.getReminders
import com.example.rememberme.viewmodels.RememberViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: RememberViewModel,
    reminderID: Long = 1
) {
    val reminder = Reminder(title = "next", d = 1, m = 1, y = 2023, h = 22, min = 0, text = "hello")//TODO: use this: viewModel.filterReminders(id = reminderID)
    //val reminder= reminderFilter(reminderID = reminderID)
    Scaffold(
        topBar = {
            TopAppBar(){
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = reminder.title) //TODO: geht das besser mit "?" ? - wie?

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
                Text(text = "${reminder.d}.")
                Text(text = "${reminder.m}.")
                Text(text = "${reminder.y} ")
                Text(text = "${reminder.h}:")
                Text(text = "${reminder.min}")
            }
            //if (reminder.text != null)
            Text(text = reminder.text) //TODO: geht das besser mit "?" ? - wie?
        }
    }
}


//TODO: not in use atm
/*
fun reminderFilter(reminderID: String?): Reminder {
    return getReminders().filter { reminder -> reminder.id == reminderID }[0]
}

 */