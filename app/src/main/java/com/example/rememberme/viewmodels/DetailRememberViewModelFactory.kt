package com.example.rememberme.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.rememberme.repositories.RememberRepository

class DetailRememberViewModelFactory(private val repository: RememberRepository, private val workManager: WorkManager, private val reminderID: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailRememberViewModel::class.java)) {
            return DetailRememberViewModel(repository = repository, workManager = workManager, reminderID = reminderID) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}