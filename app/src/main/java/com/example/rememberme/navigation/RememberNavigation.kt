package com.example.rememberme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rememberme.screens.DetailScreen
import com.example.rememberme.screens.HomeScreen
import com.example.rememberme.viewmodels.RememberViewModel

@Composable
fun RememberNavigation() {
    val navController = rememberNavController()
    val rememberViewModel: RememberViewModel = viewModel()
    rememberViewModel.reminders

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
                type = NavType.StringType
            })
        ) {
            //get argument from backstack and pass as argument to detailscreen
                backStackEntry ->
            DetailScreen(
                reminderID = backStackEntry.arguments?.getString("reminderID"),
                navController = navController,
                viewModel = rememberViewModel
            )
        }
    }
}