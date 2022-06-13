package com.example.rememberme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rememberme.repositories.RememberRepository

class EditRememberViewModelFactory (private val repository: RememberRepository, private val reminderID: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditRememberViewModel::class.java)) {
            return EditRememberViewModel(repository = repository, reminderID = reminderID) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}