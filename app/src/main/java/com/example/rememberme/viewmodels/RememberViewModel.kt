package com.example.rememberme.viewmodels

import android.provider.CalendarContract
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.models.Reminder
//import com.example.rememberme.models.getReminders
import com.example.rememberme.repositories.RememberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RememberViewModel(
    private val repository: RememberRepository
) : ViewModel() {
    private var _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()


    //private val _reminders = mutableStateListOf<Reminder>() //TODO: WHY? Kapselpaatern. wie funktiontiert das mit _ und get()?
    //val reminders: List<Reminder>
    //get() = _reminders

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllReminders().collect { listofReminders ->
                if(listofReminders.isNullOrEmpty()) {
                    Log.d("ViewModel", "No reminders")
                } else {
                    _reminders.value = listofReminders
                    Log.d("ViewModel", "reminder list init ok")
                }
            }
        }
/*
        _reminders.addAll(
            getReminders() //TODO: nimmt dummy-reminders. provisorisch! nach room video ersetzen
        )

 */
    }

    fun addReminder(reminder: Reminder) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addReminder(reminder = reminder)
            Log.d("ViewModel", "reminder added")
        }
    }

    fun removeReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReminder(reminder)
        }
    }

    fun getAllReminders(): Flow<List<Reminder>>{ //TODO: brauch ich das wirklich?
        return repository.getAllReminders()
    }
/*
    fun filterReminders(id: Long = 1) : Reminder {
        //TODO: run as coroutine - how return id?
        return repository.filterReminder(id) //{ reminder -> reminder.id == id }[0] //TODO: WHY? without the [0] type mismatch - why
    }

 */

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    //TODO: function for editReminder etc.
}