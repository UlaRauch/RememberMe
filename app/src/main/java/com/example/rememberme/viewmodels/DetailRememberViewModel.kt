package com.example.rememberme.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.models.Reminder
import com.example.rememberme.repositories.RememberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRememberViewModel(
    private val repository: RememberRepository
) : ViewModel() {
    private var _reminder: MutableLiveData<Reminder?> =
        MutableLiveData(Reminder(title = "", d = 0, m = 0, y = 0, h = 0, min = 0, text = ""))
    val reminder: LiveData<Reminder?> = _reminder

    fun getReminderbyID(reminderID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _reminder.postValue(repository.filterReminder(id = reminderID)) //postvalue instead of value because of coroutine https://stackoverflow.com/questions/51299641/difference-of-setvalue-postvalue-in-mutablelivedata?rq=1
        }
    }

}