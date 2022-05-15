package com.example.rememberme.viewmodels

import android.provider.CalendarContract
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.rememberme.models.Reminder
import com.example.rememberme.models.getReminders
import com.example.rememberme.repositories.RememberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RememberViewModel(
    private val repository: RememberRepository
) : ViewModel() {
    private var _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()


    //private val _reminders = mutableStateListOf<Reminder>() //TODO: WHY? Kapselpaatern. wie funktiontiert das mit _ und get()?
    //val reminders: List<Reminder>
    //get() = _reminders

    init {

        _reminders.addAll(
            getReminders() //TODO: nimmt dummy-reminders. provisorisch! nach room video ersetzen
        )
    }

    fun addReminder(reminder: Reminder) {
        repository.addReminder(reminder)
    }

    fun removeReminder(reminder: Reminder){
        repository.deleteReminder(reminder)
    }

    fun getAllReminders(): Flow<List<Reminder>>{ //TODO: brauch ich das wirklich?
        return repository.getAllReminders()
    }

    fun filterReminders(id: Long = 1) : Reminder {
        return repository.filterReminder(id) //{ reminder -> reminder.id == id }[0] //TODO: WHY? without the [0] type mismatch - why
    }

    //TODO: function for editReminder etc.
}