package com.example.rememberme.viewmodels

import android.provider.CalendarContract
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.rememberme.models.Reminder
import com.example.rememberme.models.getReminders

class RememberViewModel : ViewModel() {
    private val _reminders = mutableStateListOf<Reminder>()
    val reminders: List<Reminder>
    get() = _reminders

    init {
        _reminders.addAll(
            getReminders() //TODO: nimmt dummy-reminders. provisorisch!
        )
    }

    fun addReminder(reminder: Reminder) {
        _reminders.add(reminder)
    }

    fun removeReminder(reminder: Reminder){
        _reminders.remove(reminder)
    }

    fun getAllReminders(): List<Reminder>{ //TODO: brauch ich das wirklich?
        return _reminders
    }

    //TODO: any functions missing?
}