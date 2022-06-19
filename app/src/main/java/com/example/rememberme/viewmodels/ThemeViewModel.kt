package com.example.rememberme.viewmodels

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    private val _isDarkMode = MutableLiveData<Boolean>(false)
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    fun toggleDarkMode() {
        _isDarkMode.value?.let {
            Log.i("ThemeVM", "isDarkMode before toggle: ${_isDarkMode.value}")
            _isDarkMode.value = !_isDarkMode.value!!
            Log.i("ThemeVM", "isDarkMode after toggle: ${_isDarkMode.value}")
        }
    }
    fun setToSystemMode(isSystemInDarkTheme: Boolean) {
        _isDarkMode.value?.let {
            Log.i("ThemeVM", "isDarkMode before set to systemMode: ${_isDarkMode.value}")
            _isDarkMode.value = isSystemInDarkTheme
            Log.i("ThemeVM", "isDarkMode after set to systemMode: ${_isDarkMode.value}")
        }
    }

}
