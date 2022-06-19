package com.example.remindme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.remindme.models.Reminder
import com.example.remindme.repositories.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val repository: ReminderRepository,
    private val workManager: WorkManager
) : ViewModel() {
    private var _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllReminders().collect { listofReminders ->
                if (listofReminders.isNullOrEmpty()) {
                    _reminders.value =
                        emptyList()
                   // Log.d("Delete ViewModel", "No reminders")
                } else {
                    _reminders.value = listofReminders
                   // Log.d("Delete ViewModel", "reminder list init ok")
                }
               // Log.i("Delete ViewModel", "reminders left in list: ${reminders.value}")
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
            Thread.sleep(1000)
        }
        workManager.cancelAllWork()
    }
}