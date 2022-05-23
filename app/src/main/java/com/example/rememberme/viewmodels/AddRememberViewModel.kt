package com.example.rememberme.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.models.Reminder
import com.example.rememberme.repositories.RememberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddRememberViewModel(
    private val repository: RememberRepository
    ): ViewModel() {

    // val reminder: Reminder

    private var _reminder: MutableLiveData<Reminder?> =
        MutableLiveData(Reminder(title = "", d = 0, m = 0, y = 0, h = 0, min = 0, text = ""))
    val reminder: LiveData<Reminder?> = _reminder
/* von mir mit leon erarbeitet
    fun setText(text: String) {
        _reminder.value?.text = text //wenn string übergeben wird wird geschaut ob eh nicht null
        val y= 0
    }

    fun addReminder() { //brauche keinen parameter weil alles im viewmodel is
        viewModelScope.launch(Dispatchers.IO) {
            if(reminder.value != null) {
                repository.addReminder(reminder.value!!)
                Log.d("ViewModel", "reminder added")
            }
        }
    }
*/
/*von leons branch*/
    fun setText(text: String) {
        _reminder.value?.text = text //wenn string übergeben wird wird geschaut ob eh nicht null
    }

    fun addReminder() { //brauche keinen parameter weil alles im viewmodel is

        viewModelScope.launch(Dispatchers.IO) {
            reminder.value?.let { // call block only if not null
                if (it.text.isNotEmpty()) {  // add more "Pflichtfelder" here if necessary
                    repository.addReminder(reminder.value!!)//
                    Log.d("ViewModel", "reminder added")
                }
            }
        }
    }
}


