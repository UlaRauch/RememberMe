package com.example.remindme.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.remindme.repositories.RememberRepository

class RememberViewModelFactory(
    private val repository: RememberRepository, private val workManager: WorkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RememberViewModel::class.java)) {
            return RememberViewModel(repository = repository, workManager = workManager) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}