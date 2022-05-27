package com.example.rememberme.utils

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * code by https://dev.to/blazebrain/building-a-reminder-app-with-local-notifications-using-workmanager-api-385f
 */
class RememberWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        NotificationHelper(context = context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())
        return Result.success()
    }
}