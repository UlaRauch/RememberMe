package com.example.rememberme.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rememberme.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface RememberDao {

    @Insert
    fun addReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("SELECT * from reminders")
    fun getReminders(): Flow<List<Reminder>> //Flow: stream of data, can be read continuously

    @Query("SELECT * from reminders where id=:id")
    fun getReminderByID(id: Long): Reminder

    @Query("DELETE FROM reminders")
    fun deleteAll()

    //TODO: add functions for editReminder etc.
    /*
    @Update
    fun editReminder(reminder: Reminder)
     */
}