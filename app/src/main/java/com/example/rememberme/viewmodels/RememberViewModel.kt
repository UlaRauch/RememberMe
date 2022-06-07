package com.example.rememberme.viewmodels

import android.content.Context
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
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
    private val repository: RememberRepository,
    private val context: Context //TODO: This field leaks a context object - was ist das Problem?
    ) : ViewModel() {
    private var _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllReminders().collect { listofReminders ->
                if(listofReminders.isNullOrEmpty()) {
                    _reminders.value = emptyList() //TODO: still causes app to crash after deleting single reminder when going back to homescreen
                    Log.d("Delete ViewModel", "No reminders")
                } else {
                    _reminders.value = listofReminders
                    Log.d("Delete ViewModel", "reminder list init ok")
                }
                Log.i("Delete ViewModel", "reminders left in list: ${reminders.value}")
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
        }
        WorkManager.getInstance(context).cancelAllWork()
    }
}