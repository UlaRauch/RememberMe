package com.example.rememberme.screens

import android.content.Context
import android.text.Layout
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.rememberme.models.Reminder
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.ui.theme.Purple200
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.viewmodels.DetailRememberViewModel
import com.example.rememberme.viewmodels.DetailRememberViewModelFactory
import com.example.rememberme.viewmodels.EditRememberViewModel
//import com.example.rememberme.models.getReminders
import com.example.rememberme.viewmodels.RememberViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    repository: RememberRepository,
    workManager: WorkManager,
    reminderID: Long = 1
) {
    val viewModel: DetailRememberViewModel = viewModel(
        factory = DetailRememberViewModelFactory(repository = repository, workManager = workManager, reminderID = reminderID)
    )
    val reminder by viewModel.reminder.observeAsState() // observe the reminder state

    Log.i("edit", "reminder in detail: ${reminder?.title}")
    viewModel.getRemindersDEBUG()

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Purple600){
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                    reminder?.let {
                        Text(text = it.title, textAlign = TextAlign.Center, modifier = Modifier.absolutePadding(10.dp,0.dp,20.dp,0.dp))
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Reminder",
                            modifier = Modifier.clickable {
                                    viewModel.removeReminder(reminder = it, tag = reminderID.toString())
                                    navController.popBackStack()
                                }
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Reminder",
                            modifier = Modifier
                                .absolutePadding(10.dp,0.dp,0.dp,0.dp)
                                .clickable {
                                navController.navigate(RememberScreens.EditScreen.name + "/$reminderID")
                                //TODO
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
fun MainContentD(reminder:Reminder, reminderID: Long = 1) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        //editViewModel.getReminderbyID(reminderID = reminderID)
        //val reminder2 = editViewModel.reminder

        Column() {

            Text(
                text = reminder.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)

            )

            Row() {
                Text(
                    text = "Date: ${reminder.d}.${reminder.m+1}.${reminder.y}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.absolutePadding(10.dp, 0.dp, 0.dp, 5.dp)
                )
            }
            Row() {
                Text(
                    text = "Time: ${reminder.h}:${reminder.min}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.absolutePadding(10.dp, 0.dp, 0.dp, 0.dp)
                )

            }

            //if (reminder.text != null)
            Divider(modifier = Modifier.padding(7.dp))
            Text(
                text = reminder.text,
                modifier = Modifier.absolutePadding(10.dp, 0.dp, 0.dp, 0.dp)
            )
                //TODO: geht das besser mit "?" ? - wie?
            /*if (reminder != reminder2.value) {
                Text(
                    text = reminder2.value!!.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        .padding(0.dp, 10.dp)
                )

                Row() {
                    Text(
                        text = "Date: ${reminder2.value?.d}.${reminder2.value?.m}.${reminder2.value?.y}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.absolutePadding(10.dp, 0.dp, 0.dp, 5.dp)
                    )
                }
                Row() {
                    Text(
                        text = "Time: ${reminder2.value?.h}:${reminder2.value?.min}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.absolutePadding(10.dp, 0.dp, 0.dp, 0.dp)
                    )

                }
                    //if (reminder.text != null)
                Divider(modifier = Modifier.padding(7.dp))
                Text(
                    text = reminder2.value!!.text,
                    modifier = Modifier.absolutePadding(10.dp, 0.dp, 0.dp, 0.dp)
                )
            */
            }
        }
}


//TODO: not in use atm
/*
fun reminderFilter(reminderID: String?): Reminder {
    return getReminders().filter { reminder -> reminder.id == reminderID }[0]
}

 */