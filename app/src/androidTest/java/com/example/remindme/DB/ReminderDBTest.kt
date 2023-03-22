package com.example.remindme.DB

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.remindme.models.Reminder
import junit.framework.TestCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth
/*
following this tutorial
https://www.geeksforgeeks.org/testing-room-database-in-android-using-junit/
 */
@RunWith(AndroidJUnit4::class)
class ReminderDBTest : TestCase() {
    private lateinit var db: ReminderDB
    private lateinit var dao: ReminderDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ReminderDB::class.java).build()
        dao = db.remindersDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun writeAndReadReminder() = runBlocking {
        val reminder = Reminder(title = "TestReminder", d = 22, m = 3, y = 2023, h = 20, min = 30, text = "test")
        dao.addReminder(reminder = reminder)
        val reminders = dao.getReminders().toList()
        //using hamcrest instead of truth
        assertThat(reminders, hasItem(reminder))
    }
}