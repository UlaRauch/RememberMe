package com.example.remindme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.remindme.repositories.ReminderRepository

class DetailReminderViewModelFactory(private val repository: ReminderRepository, private val workManager: WorkManager, private val reminderID: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailReminderViewModel::class.java)) {
            return DetailReminderViewModel(repository = repository, workManager = workManager, reminderID = reminderID) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}