package com.example.rememberme.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.rememberme.models.Reminder
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.viewmodels.RememberViewModel
import com.example.rememberme.viewmodels.RememberViewModelFactory
import com.example.rememberme.viewmodels.ThemeViewModel
import com.example.rememberme.widgets.RememberRow

/**
 * Creats a TopAppBar for Home screen
 *
 * @param navController
 * @param repository: RemeberRepository
 * @param workManager
 * @param onDarkModeToggle
 * @param themeViewModel
 */

@Composable
fun HomeScreen(
    navController: NavController,
    repository: RememberRepository,
    workManager: WorkManager,
    //onDarkModeToggle: () -> Unit = {},
    themeViewModel: ThemeViewModel
) {
    val viewModel: RememberViewModel = viewModel(
        factory = RememberViewModelFactory(repository = repository, workManager = workManager)
    )

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "RemindMe") },
            actions = {
                IconButton(onClick = { viewModel.deleteAll() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete all reminders"
                    )
                }
                DarkModeDropDown(themeViewModel = themeViewModel)
            }
        )
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = RememberScreens.AddScreen.name)
                },
                modifier = Modifier.size(80.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Button",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) {
        val reminders: List<Reminder> by viewModel.reminders.collectAsState()
        MainContent(
            navController = navController,
            reminders = reminders
        )
    }
}

/**
 * Dropdown menu for dark and light theme
 * @param onDarkModeToggle
 * @param themeViewModel
 */
@Composable
fun DarkModeDropDown(
    //onDarkModeToggle: () -> Unit = {},
    themeViewModel: ThemeViewModel
) {
    //TODO: make this stateful, so system change will be recognized immediately
    val isSystemInDarkTheme = isSystemInDarkTheme()
    var showMenu by remember { mutableStateOf(false) }
    Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = "Dropdown menu for dark/light mode settings",
        modifier = Modifier.clickable {
            showMenu = !showMenu
            //TODO
        })
    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
        //Toggle manually
        DropdownMenuItem(onClick = {
            Log.i("Home", "dark mode toggle clicked")
            themeViewModel.toggleDarkMode()
            //onDarkModeToggle()
        }) {
            Row() {
                //Icon for toggle dark light mode
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Button Dark/Light Mode",
                    modifier = Modifier
                        .padding(12.dp)
                )
                Text(text = "Toggle dark mode")
            }
        }
        //Use System Theme
        DropdownMenuItem(onClick = {
            Log.i("Home", "system mode clicked")
            themeViewModel.setToSystemMode(isSystemInDarkTheme)
        }) {
            Row() {
                //Icon for system dark light mode
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Button system dark/light mode",
                    modifier = Modifier
                        .padding(12.dp)
                )
                Text(text = "Use system mode")
            }
        }
    }
}

/**
 * Content for Home screen
 * @param navController
 * @param reminders to display the reminders
 */
@Composable
fun MainContent(
    navController: NavController,
    reminders: List<Reminder>
) {
    LazyColumn() {
        items(reminders) { reminder ->
            RememberRow(
                reminder = reminder,
            )
            { reminderID ->
                //navController.navigate("HomeScreen")
                if (reminderID != null) {
                    navController.navigate(RememberScreens.DetailScreen.name + "/$reminderID")
                    Log.d("Navigation", "Reminder clicked. ID: ${reminder.id}")
                } else Log.d("Navigation", "No ID")
            }
        }

    }
}