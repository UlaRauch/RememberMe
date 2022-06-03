package com.example.rememberme.DB

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.rememberme.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface RememberDao {

    @Insert
    fun addReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Update
    fun updateReminder(reminder: Reminder)

    @Query("SELECT * from reminders")
    fun getReminders(): Flow<List<Reminder>> //Flow: stream of data, can be read continuously

    @Query("SELECT * from reminders where id=:id")
    fun getReminderByID(id: Long): Reminder?

    @Query("DELETE FROM reminders")
    fun deleteAll()

    //TODO: add functions for editReminder etc.
    /*
    @Update
    fun editReminder(reminder: Reminder)
     */
}