package com.example.rememberme.widgets

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rememberme.models.Reminder
import com.example.rememberme.screens.reminderFilter

@Composable
fun RememberRow(
    reminder: Reminder = reminderFilter(reminderID = "1"),
    onItemClick: (String) -> Unit = {}
    ){
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
                    //.size(100.dp),

                ) {
                Column() {

                    Text(
                        text = reminder.title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
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
}