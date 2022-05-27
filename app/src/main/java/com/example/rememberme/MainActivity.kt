package com.example.rememberme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.rememberme.navigation.RememberNavigation
import com.example.rememberme.ui.theme.RememberMeTheme
import com.example.rememberme.utils.RememberWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RememberNavigation()
                }
            }
        }
    }

    /**
     * Begin by https://dev.to/blazebrain/building-a-reminder-app-with-local-notifications-using-workmanager-api-385f
     */
    private fun createWorkRequest(title: String, message: String, timeDelayInSeconds: Long) {
        val workRequest = OneTimeWorkRequestBuilder<RememberWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "title" to title,
                "message" to message
            ))
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
    /**
     * End
     */
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RememberMeTheme {
        //HomeScreen(navController = n)
    }
}