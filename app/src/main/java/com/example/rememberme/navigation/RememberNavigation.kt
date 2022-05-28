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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.rememberme.DB.RememberDB
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.screens.AddScreen
import com.example.rememberme.screens.DetailScreen
import com.example.rememberme.screens.HomeScreen
import com.example.rememberme.utils.RememberWorker
import com.example.rememberme.viewmodels.*
import java.util.concurrent.TimeUnit

@Composable
fun RememberNavigation() {
    val context = LocalContext.current
    val db = RememberDB.getDatabase(context = context)
    val repository = RememberRepository(db.remindersDao())
    val navController = rememberNavController()
    val rememberViewModel: RememberViewModel = viewModel(
        factory = RememberViewModelFactory(repository = repository)
    )
    val addViewModel: AddRememberViewModel = viewModel(
        factory = AddRememberViewModelFactory(repository = repository)
    )
    val detailViewModel: DetailRememberViewModel = viewModel(
        factory = DetailRememberViewModelFactory(repository = repository)
    )

    rememberViewModel.reminders
    addViewModel.reminder


    NavHost(
        navController = navController,
        startDestination = RememberScreens.HomeScreen.name)
    {
        composable(RememberScreens.HomeScreen.name) {
            HomeScreen(navController = navController, viewModel = rememberViewModel)
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
                viewModel = detailViewModel
            )
        }
        composable(RememberScreens.AddScreen.name) {
            AddScreen(navController = navController, addViewModel = addViewModel, context = context)
        }

    }
}

