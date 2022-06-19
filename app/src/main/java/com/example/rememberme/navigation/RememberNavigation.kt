package com.example.rememberme.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.rememberme.DB.RememberDB
import com.example.rememberme.models.Reminder
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.screens.AddScreen
import com.example.rememberme.screens.DetailScreen
import com.example.rememberme.screens.EditScreen
import com.example.rememberme.screens.HomeScreen
import com.example.rememberme.utils.RememberWorker
import com.example.rememberme.viewmodels.*

@Composable
fun RememberNavigation(themeViewModel: ThemeViewModel) {
    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)
    val db = RememberDB.getDatabase(context = context)
    val repository = RememberRepository(db.remindersDao())
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RememberScreens.HomeScreen.name
    )
    {
        composable(RememberScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                repository = repository,
                workManager = workManager,
                themeViewModel = themeViewModel
            )
        }

        composable(
            route = RememberScreens.DetailScreen.name + "/{reminderID}",
            arguments = listOf(navArgument("reminderID") {
                type = NavType.LongType
            })
        ) {
            //get argument from backstack and pass as argument to detailscreen
                backStackEntry ->
            DetailScreen(
                reminderID = backStackEntry.arguments?.getLong("reminderID")!!,
                navController = navController,
                repository = repository,
                workManager = workManager
            )
        }

        composable(RememberScreens.AddScreen.name) {
            AddScreen(
                navController = navController,
                repository = repository,
                workManager = workManager,
                context = context
            )
        }

        composable(
            route = RememberScreens.EditScreen.name + "/{reminderID}",
            arguments = listOf(navArgument("reminderID") {
                type = NavType.LongType
            })
        ) {
            //get argument from backstack and pass as argument to detailscreen
                backStackEntry ->
            EditScreen(
                reminderID = backStackEntry.arguments?.getLong("reminderID")!!,
                repository = repository,
                navController = navController,
                workManager = workManager,
                context = context
            )
        }

    }
}

