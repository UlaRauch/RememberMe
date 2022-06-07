package com.example.rememberme.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rememberme.repositories.RememberRepository

class RememberViewModelFactory(private val repository: RememberRepository, private val context: Context //TODO: This field leaks a context object - was ist das Problem?
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RememberViewModel::class.java)) {
            return RememberViewModel(repository = repository, context = context) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}