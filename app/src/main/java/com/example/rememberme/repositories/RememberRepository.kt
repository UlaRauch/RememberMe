package com.example.rememberme.repositories

import com.example.rememberme.DB.RememberDao
import com.example.rememberme.models.Reminder
import kotlinx.coroutines.flow.Flow

class RememberRepository(private val dao: RememberDao) {

    fun addReminder(reminder: Reminder) = dao.addReminder(reminder = reminder)

    fun deleteReminder(reminder: Reminder) = dao.deleteReminder(reminder = reminder)

    fun getAllReminders(reminder: Reminder): Flow<List<Reminder>> = dao.getReminders()

    //TODO: add functions here for editReminder oder deleteAllReminders
}