package com.example.rememberme.screens

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rememberme.models.Reminder
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.ui.theme.Green600
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.viewmodels.AddRememberViewModel
import java.util.*


@Composable
fun AddScreen(
    navController: NavController,
    addViewModel: AddRememberViewModel,
    context: Context
) {
    //val reminder = Reminder(title = "next", d = 1, m = 1, y = 2023, h = 22, min = 0, text = "")
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Purple600, contentColor = Color.White
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
                
                }

            }
        }, 
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addViewModel.addReminder()
                navController.navigate(route = RememberScreens.HomeScreen.name)
            },
                backgroundColor = Green600,
                modifier = Modifier.size(80.dp)
            ) {


                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Add Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )

            }
        }
    ){
        ReminderCard(addViewModel = addViewModel, context = context)
    }

}
/* Von mir mit leon bearbeitet
@Composable
fun ReminderCard(addViewModel: AddRememberViewModel) { //daweil kopiert und etwas geaendert muss ich mir genauer anschauen!!
    //var text by remember { mutableStateOf("") }
    val reminder: Reminder? by addViewModel.reminder.observeAsState(null)
    //addViewModel.reminder.observe(LocalContext.current,{x -> Log.d("AddScreen","In ReminderCard")})
    OutlinedTextField(
        value = if (reminder != null) reminder!!.text else "", //schaut is reminder nicht null wenns da is dann wird der vom viewmodel angezeigt
        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "EditIcon") },
        //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        onValueChange = {value -> addViewModel.setText(value)}, // immer wenn ich text änder dann ändert sich das im reminderobjekt aktuallisiert
        label = { Text(text = "Reminder") },
        placeholder = { Text(text = "Enter Text") },
    )

}
 */

@Composable
fun ReminderCard(addViewModel: AddRememberViewModel, context: Context){
    var text by remember { mutableStateOf("") }
    // add more properties if you need
    var date by remember { mutableStateOf("") }
    /*var mDay = remember { mutableStateOf("") }
    var mMonth = remember { mutableStateOf("") }
    var mYear = remember { mutableStateOf("") }
     */
    var y: Int
    var m: Int
    var d: Int
    val now = Calendar.getInstance()

    y = now.get(Calendar.YEAR)
    m = now.get(Calendar.MONTH)
    d = now.get(Calendar.DAY_OF_MONTH)
    now.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            y = year
            m = month+1
            d = dayOfMonth
            addViewModel.setDate(d = d,m = m, y = y)
            //date = "$dayOfMonth/$month/$year"
        }, y, m, d
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp,30.dp),
        verticalArrangement = Arrangement.Center,
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            datePickerDialog.show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Open Date Picker")
        }
        Spacer(modifier = Modifier.size(16.dp))

        //addViewModel.setDate(d,m,y) // gives the date of day not selected date but i dunno how
        //Text(text = "Selected date: ${mDay.value}.${mMonth.value}.${mYear.value}") //--> date.value is teh selected date
    }

    //DatePickerDemo(context = context, addViewModel = addViewModel)

    /* von leons Branch*/
    val reminder: Reminder? by addViewModel.reminder.observeAsState(null)
    OutlinedTextField(
        //value = if (reminder != null) reminder!!.text else "", //schaut is reminder nicht null wenns da is dann wird der vom viewmodel angezeigt
        value = text,
        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "EditIcon") },
        // trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        // onValueChange = {value -> addViewModel.setText(value)}, // immer wenn ich text änder dann ändert sich das im reminderobjekt aktuallisiert
        onValueChange = { value ->
            text = value    // update text value inside field
            addViewModel.setText(value) // update value in viewmodel
        },
        label = { Text(text = "Reminder") },
        placeholder = { Text(text = "Enter Text") },
        modifier = Modifier
            .padding(20.dp, 80.dp)
            .fillMaxWidth()
    )

}
