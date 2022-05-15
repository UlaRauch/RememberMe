package com.example.rememberme.viewmodels

import android.provider.CalendarContract
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.rememberme.models.Reminder
import com.example.rememberme.models.getReminders

class RememberViewModel : ViewModel() {
    private val _reminders = mutableStateListOf<Reminder>() //TODO: WHY? wie funktiontiert das mit _ und get()?
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

    fun getAllReminders(): List<Reminder>{ //TODO: WHY? brauch ich das wirklich?
        return _reminders
    }

    fun filterReminders(id: String? = "1") : Reminder {
        return _reminders.filter { reminder -> reminder.id == id }[0] //TODO: WHY? without the [0] type mismatch - why
    }

    //TODO: any functions missing?
}