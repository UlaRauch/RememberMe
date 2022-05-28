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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllReminders().collect { listofReminders ->
                if(listofReminders.isNullOrEmpty()) {
                    _reminders.value = emptyList()
                    Log.d("ViewModel", "No reminders")
                } else {
                    _reminders.value = listofReminders
                    Log.d("ViewModel", "reminder list init ok")
                }
            }
        }
    }


    fun getAllReminders(): Flow<List<Reminder>>{ //TODO: brauchen wir das noch?
        return repository.getAllReminders()
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()

            Thread.sleep(1000)
            Log.i("ViewModel", "reminders left in list: ${reminders.value}")
        }
    }
}