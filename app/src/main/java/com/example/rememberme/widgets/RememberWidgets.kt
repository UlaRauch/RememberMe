package com.example.rememberme.widgets

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rememberme.models.Reminder
//import com.example.rememberme.screens.reminderFilter

@Composable
fun RememberRow(
    //reminder: Reminder = reminderFilter(reminderID = "1"),
    reminder: Reminder,
    onItemClick: (Long?) -> Unit = {}
    ){
    Log.d("RememberWidgets", "Reminder widget called")
//    val reminder= reminderFilter(reminderId = "1")
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .heightIn(min = 0.dp)
            .clickable {onItemClick(reminder.id)},
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)

                ) {
                Column() {

                    Text(
                        text = reminder.title!!,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row() {
                        Text(
                            text = "Date: ${reminder.d}.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "${reminder.m}.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "${reminder.y}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Row() {
                        Text(
                            text = "Time: ${reminder.h}:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "${reminder.min}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

/*
                    Text(
                        text = "Text: ${reminder.text}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )

 */
                }
            }
        }
    }
}