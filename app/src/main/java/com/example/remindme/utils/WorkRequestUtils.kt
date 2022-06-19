package com.example.remindme.utils

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.remindme.models.Reminder
import java.util.concurrent.TimeUnit

object WorkRequestUtils {
    /*
     Begin code by https://dev.to/blazebrain/building-a-reminder-app-with-local-notifications-using-workmanager-api-385f
     adapted to less params by Ula Rauch
     */
    fun createWorkRequest(reminder: Reminder, workManager: WorkManager) {
        val timeDelayInSeconds = getDelayInSeconds(reminder)
        val workRequest = OneTimeWorkRequestBuilder<RememberWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to reminder.title,
                    "message" to reminder.text
                )
            )
            .addTag(reminder.id.toString())
            .build()
        workManager.enqueue(workRequest)
        // Log.i("Delete Add", "enqueuing work with tag: ${reminder.id}")
    }

    fun getDelayInSeconds(reminder: Reminder): Long {
        val userDateTime = Calendar.getInstance()
        userDateTime.set(reminder.y, reminder.m, reminder.d, reminder.h, reminder.min)
        val now = Calendar.getInstance()
        //Log.i("ViewModel Add", "usertime: $userDateTime")
        //Log.i("ViewModel Add", "seconds left: ${(userDateTime.timeInMillis / 1000L) - (now.timeInMillis / 1000L)}")
        return (userDateTime.timeInMillis / 1000L) - (now.timeInMillis / 1000L)
    }
    /*
     End
     */
}