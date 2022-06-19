package com.example.remindme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.remindme.repositories.ReminderRepository

class ReminderViewModelFactory(
    private val repository: ReminderRepository, private val workManager: WorkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(repository = repository, workManager = workManager) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}