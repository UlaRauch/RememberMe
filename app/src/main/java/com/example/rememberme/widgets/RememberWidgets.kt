package com.example.rememberme.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rememberme.screens.reminderFilter

@Composable
fun RememberRow(){
    val reminder= reminderFilter(reminderId = "1")
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row() {
            Column {

                Text(
                    text = reminder.title,
                    style = MaterialTheme.typography.h6
                )
                Row() {
                    Text(
                        text = "Date: ${reminder.day}.",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "${reminder.month}.",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "${reminder.year}",
                        style = MaterialTheme.typography.caption
                    )
                }

                Text(
                    text = "Text: ${reminder.text}",
                    style = MaterialTheme.typography.caption
                )
                }

        }
    }
}