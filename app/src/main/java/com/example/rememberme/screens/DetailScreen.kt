package com.example.rememberme.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rememberme.models.Reminder
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.ui.theme.Purple200
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.viewmodels.DetailRememberViewModel
import com.example.rememberme.viewmodels.DetailRememberViewModelFactory
//import com.example.rememberme.models.getReminders
import com.example.rememberme.viewmodels.RememberViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    repository: RememberRepository,
    reminderID: Long = 1
) {
    val viewModel: DetailRememberViewModel = viewModel(
        factory = DetailRememberViewModelFactory(repository = repository, rememberId = reminderID)
    )

    val reminder by viewModel.reminder.observeAsState() // observe the reminder state

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Purple600){
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            //navController.navigate(RememberScreens.HomeScreen.name)
                            navController.popBackStack()
                        }
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    // save wrapping possible null values
                    reminder?.let {
                        Text(text = it.title)

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                viewModel.removeReminder(reminder = it)
                            }
                        )
                    }
                }
            }
        }) {

        reminder?.let {
            MainContentD(
                reminder = it
            )
        }
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
            Text(text = reminder.text)
        }
    }
}


//TODO: not in use atm
/*
fun reminderFilter(reminderID: String?): Reminder {
    return getReminders().filter { reminder -> reminder.id == reminderID }[0]
}

 */