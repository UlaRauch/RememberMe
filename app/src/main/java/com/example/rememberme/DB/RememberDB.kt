package com.example.rememberme.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.rememberme.models.Reminder

@Database(
    entities = [Reminder::class], //add entities here
    version = 1, //update with each change in db
    exportSchema = false
)
abstract class RememberDB : RoomDatabase() {

    abstract fun remindersDao(): RememberDao

    companion object {
        private var INSTANCE: RememberDB? = null

        fun getDatabase(context: Context): RememberDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { //if DB doesn't exist yet, create DB
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): RememberDB {
            return Room
                .databaseBuilder(context, RememberDB::class.java, "reminders_database")
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