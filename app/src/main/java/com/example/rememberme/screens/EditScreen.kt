package com.example.rememberme.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.ui.theme.Green600
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.viewmodels.DetailRememberViewModel


@Composable
fun EditScreen(
    navController: NavController,
    viewModel: DetailRememberViewModel,
    reminderID: Long = 1,
    context: Context
) {
    //val reminder = Reminder(title = "next", d = 1, m = 1, y = 2023, h = 22, min = 0, text = "")
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Purple600, contentColor = Color.White
            ) {
                Row(){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )

                    Text( text = "New Reminder",
                        modifier = Modifier.absolutePadding(110.dp, 0.dp,110.dp,0.dp))

                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
               /* addViewModel.addReminder()

                val delayInSeconds = getDelayInSeconds(
                    addViewModel.reminder.value!!.y,
                    addViewModel.reminder.value!!.m -1, //months are represented as index https://developer.android.com/reference/java/util/Date.html#Date%28int,%20int,%20int,%20int,%20int,%20int%29
                    addViewModel.reminder.value!!.d,
                    addViewModel.reminder.value!!.h,
                    addViewModel.reminder.value!!.min,
                )
                createWorkRequest(
                    addViewModel.reminder.value!!.title,
                    addViewModel.reminder.value!!.text,
                    delayInSeconds,
                    context)*/
                navController.navigate(route = RememberScreens.HomeScreen.name)
            },
                backgroundColor = Green600,
                modifier = Modifier.size(80.dp)
            ) {

                //if() { wenn bei zeit und datum nihts geklickt dann soll datum und zeit zum erstellpunkt genommen werden

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Add Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                //  }

            }
        }
    ){
        context
    //ReminderCard(addViewModel = addViewModel, context = context)
    }

}