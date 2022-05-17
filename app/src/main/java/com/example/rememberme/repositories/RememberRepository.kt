package com.example.rememberme.repositories

import com.example.rememberme.DB.RememberDao
import com.example.rememberme.models.Reminder
import kotlinx.coroutines.flow.Flow

class RememberRepository(private val dao: RememberDao) {

    suspend fun addReminder(reminder: Reminder) = dao.addReminder(reminder = reminder)

    suspend fun deleteReminder(reminder: Reminder) = dao.deleteReminder(reminder = reminder)

    fun getAllReminders(): Flow<List<Reminder>> = dao.getReminders()

    fun filterReminder(id: Long) = dao.getReminderByID(id = id)

    suspend fun deleteAll() = dao.deleteAll()

    //TODO: add functions here for editReminder oder deleteAllReminders
}