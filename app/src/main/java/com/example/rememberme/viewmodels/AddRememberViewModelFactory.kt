package com.example.rememberme.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rememberme.repositories.RememberRepository

class AddRememberViewModelFactory(private val repository: RememberRepository, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddRememberViewModel::class.java)) {
            return AddRememberViewModel(repository = repository, context = context) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}