package com.example.remindme.widgets

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
import com.example.remindme.models.Reminder

/**
 * Card for a reminder
 * @param reminder
 * @param onItemClick
 */
@Composable
fun RememberRow(
    reminder: Reminder,
    onItemClick: (Long?) -> Unit = {}
) {
    //Log.d("RememberWidgets", "Reminder widget called")

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .heightIn(min = 0.dp)
            .clickable { onItemClick(reminder.id) },
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
                        text = reminder.title,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (reminder.isSurprise) {
                        Text(text = "Surprise date")
                    } else {
                        Row() {
                            Text(
                                text = "Date: ${
                                    reminder.d.toString().padStart(2, '0')
                                }.${(reminder.m + 1).toString().padStart(2, '0')}.${reminder.y}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        Row() {
                            Text(
                                text = "Time: ${
                                    reminder.h.toString().padStart(2, '0')
                                }:${reminder.min.toString().padStart(2, '0')}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }
        }
    }
}