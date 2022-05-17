package com.example.rememberme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rememberme.repositories.RememberRepository

class RememberViewModelFactory(private val repository: RememberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RememberViewModel::class.java)) {
            return RememberViewModel(repository = repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}