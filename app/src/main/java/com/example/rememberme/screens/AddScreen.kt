package com.example.rememberme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rememberme.ui.theme.Purple200
import com.example.rememberme.viewmodels.RememberViewModel


@Composable
fun AddScreen(
    navController: NavController,
    viewModel: RememberViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Purple200, contentColor = Color.White
            ) {
                Row(){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )

                    Text( text = "New Reminder",
                    modifier = Modifier.absolutePadding(110.dp, 0.dp,110.dp,0.dp))

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Button",
                        modifier = Modifier.clickable {
                            //navController.popBackStack()
                        }
                    )
                }

            }
        }
    ){
        ReminderTextField()
    }

}

@Composable
fun ReminderTextField() { //daweil kopiert und etwas geaendert muss ich mir genauer anschauen!!
    var text by remember { mutableStateOf(TextFieldValue("")) }
    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "EditIcon") },
        //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        onValueChange = {
            text = it
        },
        label = { Text(text = "Your Reminder TextField") },
        placeholder = { Text(text = "Enter your Text") },
    )
}