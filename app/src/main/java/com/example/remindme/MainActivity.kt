package com.example.remindme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.remindme.navigation.RememberNavigation
import com.example.remindme.ui.theme.RemindMeTheme
import com.example.remindme.viewmodels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //for dark/light mode settings
            val themeViewModel = ThemeViewModel(isSystemInDarkTheme())
            val isDarkMode = themeViewModel.isDarkMode.observeAsState(initial = true)

            RemindMeTheme(
                    darkTheme = isDarkMode
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        RememberNavigation(themeViewModel = themeViewModel)
                    }
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //RememberMeTheme {
        //HomeScreen(navController = n)
    //}
}