package com.example.rememberme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberme.navigation.RememberNavigation
import com.example.rememberme.ui.theme.RememberMeTheme
import com.example.rememberme.viewmodels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //for dark/light mode settings
            val themeViewModel = ThemeViewModel()
            val isDarkMode = themeViewModel.isDarkMode.observeAsState(initial = true)

            RememberMeTheme(
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