package com.example.rememberme.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.rememberme.models.Reminder
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.ui.theme.Purple100
import com.example.rememberme.ui.theme.Purple200
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.ui.theme.Teal900
import com.example.rememberme.viewmodels.RememberViewModel
import com.example.rememberme.viewmodels.RememberViewModelFactory
import com.example.rememberme.widgets.RememberRow

@Composable
fun HomeScreen(
    navController: NavController,
    repository: RememberRepository,
    workManager: WorkManager,
) {
    val viewModel: RememberViewModel = viewModel(
        factory = RememberViewModelFactory(repository = repository, workManager = workManager)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                //title = "RememberMe",
                //title = { Text(text = "RememberMe") },
                backgroundColor = Purple600,
                contentColor = Color.White
            ) {
                Row() {
                    //Icon for dark light mode, modifier = Modifier.absolutePadding(0.dp, 0.dp,110.dp,0.dp)
                    Icon(imageVector = Icons.Default.Star,
                        contentDescription = "Button Dark/Light Mode",
                        modifier = Modifier
                            .absolutePadding(0.dp, 0.dp,110.dp,0.dp)
                            .clickable {

                            })
                    Text(text = "RememberMe", textAlign = TextAlign.Center, modifier = Modifier.absolutePadding(0.dp, 0.dp,110.dp,0.dp))
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "delete all reminders",
                        modifier = Modifier
                            .clickable {
                            viewModel.deleteAll()
                        })


                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = RememberScreens.AddScreen.name)
                },
                backgroundColor = Purple200,
                modifier = Modifier.size(80.dp)
            ) {


                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )

            }
        }

    ) {
        val reminders: List<Reminder> by viewModel.reminders.collectAsState()
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