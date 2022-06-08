package com.example.rememberme.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rememberme.ui.theme.Green600
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.viewmodels.EditRememberViewModel
import java.util.*


@Composable
fun EditScreen(
    navController: NavController,
    editViewModel: EditRememberViewModel,
    reminderID: Long = 1,
    context: Context
) {

    editViewModel.getReminderbyID(reminderID = reminderID)
    val reminder = editViewModel.reminder

    Log.d("editscreen", "you're in the editscreen")
    //val reminder = Reminder(title = "next", d = 1, m = 1, y = 2023, h = 22, min = 0, text = "")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit ${reminder.value?.title}",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Default.ArrowBack,"Arrow Back")
                    }
                },
                backgroundColor = Purple600,
                contentColor = Color.White
            )},floatingActionButton = {
            FloatingActionButton(onClick = {
                reminder.value?.let { editViewModel.updateReminder(reminder = it) }
                navController.popBackStack()
            },
                backgroundColor = Green600,
                modifier = Modifier.size(80.dp)
            ) {

                //if() { wenn bei zeit und datum nihts geklickt dann soll datum und zeit zum erstellpunkt genommen werden

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Add Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        content = {

            EditReminderCard(editViewModel = editViewModel, context =  context)
             //ReminderCard(addViewModel = addViewModel, context = context)
        })
}

@Composable
fun EditReminderCard(
    editViewModel: EditRememberViewModel,
    reminderID: Long = 1,
    context: Context,
) {
    editViewModel.getReminderbyID(reminderID = reminderID)
    val reminder = editViewModel.reminder //to access text, title, date or time of the selected reminder
    Log.d("EditReminderCard", "EditReminderCard called")
    //vars text, title
    var text by remember { mutableStateOf("${reminder.value?.text}") }
    var title by remember { mutableStateOf("") }

    //vars for date
    var y: Int
    var m: Int
    var d: Int
    val nowDate = Calendar.getInstance()

    //vals and vars for Time
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val nowTime = Calendar.getInstance() //cal means Calendar
    var h = nowTime[Calendar.HOUR_OF_DAY]
    var min = nowTime[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }


    //Title
    OutlinedTextField(
        //value = if (reminder != null) reminder!!.text else "", //schaut is reminder nicht null wenns da is dann wird der vom viewmodel angezeigt
        value = title,
        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "EditIcon") },
        // trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        // onValueChange = {value -> addViewModel.setText(value)}, // immer wenn ich text änder dann ändert sich das im reminderobjekt aktuallisiert
        onValueChange = { value ->
            title = value    // update text value inside field
           editViewModel.setTitle(value) // update value in viewmodel
        },
        label = { Text(text = "${reminder.value?.title}") },
        placeholder = { Text(text = "Edit Title") },
        modifier = Modifier
            .padding(20.dp, 30.dp)
            .fillMaxWidth()
    )

    //Date
    y = nowDate.get(Calendar.YEAR)
    m = nowDate.get(Calendar.MONTH)
    d = nowDate.get(Calendar.DAY_OF_MONTH)
    nowDate.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val calDate = Calendar.getInstance()
            calDate.set(year, month, dayOfMonth)
            y = year
            m = month+1 //months are represented as index https://developer.android.com/reference/java/util/Date.html#Date%28int,%20int,%20int,%20int,%20int,%20int%29
            d = dayOfMonth
            editViewModel.setDate(d = d,m = m, y = y)
            //date = "$dayOfMonth/$month/$year"
        }, y, m, d
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 110.dp),
        verticalArrangement = Arrangement.Center,
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            datePickerDialog.show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "${reminder.value?.d}.${reminder.value?.m}.${reminder.value?.y}")
        }
        Spacer(modifier = Modifier.size(16.dp))

        //addViewModel.setDate(d,m,y) // gives the date of day not selected date but i dunno how
        //Text(text = "Selected date: ${mDay.value}.${mMonth.value}.${mYear.value}") //--> date.value is teh selected date
    }

    //Time
    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            val calTime = Calendar.getInstance()
            calTime.set(mHour,mMinute)
            h = mHour
            min = mMinute
            editViewModel.setTime(h = h, min = min)
        }, h, min, false
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 155.dp),
        verticalArrangement = Arrangement.Center,
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            mTimePickerDialog.show()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "${reminder.value?.h}:${reminder.value?.min}")
        }
        Spacer(modifier = Modifier.size(16.dp))

    }


    //Text
    /* von leons Branch*/
   // val reminder: Reminder? by editViewModel.reminder.observeAsState(null)
    OutlinedTextField(
        //value = if (reminder != null) reminder!!.text else "", //schaut is reminder nicht null wenns da is dann wird der vom viewmodel angezeigt
        value = text,
        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "EditIcon") },
        // trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        // onValueChange = {value -> addViewModel.setText(value)}, // immer wenn ich text änder dann ändert sich das im reminderobjekt aktuallisiert
        onValueChange = { value ->
            text = value    // update text value inside field
            editViewModel.setText(value) // update value in viewmodel
        },
        label = { Text(text = "${reminder.value?.text}") },
        placeholder = { Text(text = "Edit Text") },
        modifier = Modifier
            .padding(20.dp, 200.dp)
            .fillMaxWidth()
    )
}

