package com.example.remindme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.WorkManager
import com.example.remindme.DB.RememberDB
import com.example.remindme.repositories.ReminderRepository
import com.example.remindme.screens.AddScreen
import com.example.remindme.screens.DetailScreen
import com.example.remindme.screens.EditScreen
import com.example.remindme.screens.HomeScreen
import com.example.remindme.viewmodels.*

@Composable
fun RememberNavigation(themeViewModel: ThemeViewModel) {
    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)
    val db = RememberDB.getDatabase(context = context)
    val repository = ReminderRepository(db.remindersDao())
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReminderScreens.HomeScreen.name
    )
    {
        composable(ReminderScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                repository = repository,
                workManager = workManager,
                themeViewModel = themeViewModel
            )
        }

        composable(
            route = ReminderScreens.DetailScreen.name + "/{reminderID}",
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

        composable(ReminderScreens.AddScreen.name) {
            AddScreen(
                navController = navController,
                repository = repository,
                workManager = workManager,
                context = context
            )
        }

        composable(
            route = ReminderScreens.EditScreen.name + "/{reminderID}",
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

