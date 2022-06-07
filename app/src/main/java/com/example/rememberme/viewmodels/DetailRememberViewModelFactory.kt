package com.example.rememberme.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rememberme.repositories.RememberRepository

class DetailRememberViewModelFactory(private val repository: RememberRepository, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailRememberViewModel::class.java)) {
            return DetailRememberViewModel(repository = repository, context = context) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}