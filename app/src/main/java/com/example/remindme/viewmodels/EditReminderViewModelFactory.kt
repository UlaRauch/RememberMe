package com.example.remindme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.remindme.repositories.ReminderRepository

class EditReminderViewModelFactory(
    private val repository: ReminderRepository,
    private val workManager: WorkManager,
    private val reminderId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditReminderViewModel::class.java)) {
            return EditReminderViewModel(
                repository = repository,
                workManager = workManager,
                reminderID = reminderId
            ) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}