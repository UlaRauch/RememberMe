package com.example.remindme.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.remindme.models.Reminder
import com.example.remindme.navigation.RememberScreens
import com.example.remindme.repositories.RememberRepository
import com.example.remindme.viewmodels.DetailRememberViewModel
import com.example.remindme.viewmodels.DetailRememberViewModelFactory


/**
 * Creates a TopAppBar for Detail screen
 *
 * @param navController
 * @param repository: RemeberRepository
 * @param workManager
 * @param reminderID: Long
 */

@Composable
fun DetailScreen(
    navController: NavController,
    repository: RememberRepository,
    workManager: WorkManager,
    reminderID: Long = 1
) {
    val viewModel: DetailRememberViewModel = viewModel(
        factory = DetailRememberViewModelFactory(
            repository = repository,
            workManager = workManager,
            reminderID = reminderID
        )
    )
    val reminder by viewModel.reminder.observeAsState() // observe the reminder state

    //Log.i("edit", "reminder in detail: ${reminder?.title}")
    viewModel.getRemindersDEBUG()

    Scaffold(
        topBar = {
            TopAppBar() {
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End,
                    )
                    {
                        Row() {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Reminder",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable {
                                        reminder?.let {
                                            viewModel.removeReminder(
                                                reminder = it,
                                                tag = reminderID.toString()
                                            )
                                        }
                                        navController.popBackStack()
                                    }
                            )
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Reminder",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable {
                                        navController.navigate(RememberScreens.EditScreen.name + "/$reminderID")
                                    }
                            )
                        }
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

/**
 * Content for detail screen
 * @param reminder: Reminder
 */
@Composable
fun MainContentD(reminder: Reminder) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .heightIn(min = 0.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Column() {
            Text(
                text = reminder.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(20.dp)
            )
            if (reminder.isSurprise) {
                Text(text = "Surprise date", modifier = Modifier.padding(12.dp))
            } else {
                Row(modifier = Modifier.padding(12.dp, 5.dp)) {
                    Text(
                        text = "Date: ${
                            reminder.d.toString().padStart(2, '0')
                        }.${(reminder.m + 1).toString().padStart(2, '0')}.${reminder.y}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
                Row(modifier = Modifier.padding(12.dp, 5.dp)) {
                    Text(
                        text = "Time: ${
                            reminder.h.toString().padStart(2, '0')
                        }:${reminder.min.toString().padStart(2, '0')}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    )

                }
            }
            Divider(modifier = Modifier.padding(7.dp))
            Text(
                text = reminder.text,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}