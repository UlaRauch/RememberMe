package com.example.remindme.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.remindme.models.Reminder

@Database(
    entities = [Reminder::class], //add entities here
    version = 3, //update with each change in db
    exportSchema = false
)
abstract class ReminderDB : RoomDatabase() {

    abstract fun remindersDao(): ReminderDao

    companion object {
        private var INSTANCE: ReminderDB? = null

        fun getDatabase(context: Context): ReminderDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { //if DB doesn't exist yet, create DB
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): ReminderDB {
            return Room
                .databaseBuilder(context, ReminderDB::class.java, "reminders_database")
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // do work on first db creation
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // do work on each start
                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}