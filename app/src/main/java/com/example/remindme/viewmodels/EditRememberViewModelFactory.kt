package com.example.remindme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.remindme.repositories.RememberRepository

class EditRememberViewModelFactory (private val repository: RememberRepository, private val workManager: WorkManager, private val reminderId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditRememberViewModel::class.java)) {
            return EditRememberViewModel(repository = repository, workManager = workManager, reminderID = reminderId) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}