package com.example.remindme.repositories

import androidx.lifecycle.LiveData
import com.example.remindme.DB.RememberDao
import com.example.remindme.models.Reminder
import kotlinx.coroutines.flow.Flow

class RememberRepository(private val dao: RememberDao) {

    suspend fun addReminder(reminder: Reminder): Long = dao.addReminder(reminder = reminder)

    suspend fun deleteReminder(reminder: Reminder) = dao.deleteReminder(reminder = reminder)

    suspend fun updateReminder(reminder: Reminder) = dao.updateReminder(reminder = reminder)

    fun getAllReminders(): Flow<List<Reminder>> = dao.getReminders()

    fun filterReminder(id: Long): LiveData<Reminder> = dao.getReminderByID(id = id)

    suspend fun deleteAll() = dao.deleteAll()
}