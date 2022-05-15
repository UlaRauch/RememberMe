package com.example.rememberme.repositories

import com.example.rememberme.DB.RememberDao
import com.example.rememberme.models.Reminder
import kotlinx.coroutines.flow.Flow

class RememberRepository(private val dao: RememberDao) {

    fun addReminder(reminder: Reminder) = dao.addReminder(reminder = reminder)

    fun deleteReminder(reminder: Reminder) = dao.deleteReminder(reminder = reminder)

    fun getAllReminders(): Flow<List<Reminder>> = dao.getReminders()

    fun filterReminder(id: Long) = dao.getReminderByID(id = id)

    //TODO: add functions here for editReminder oder deleteAllReminders
}