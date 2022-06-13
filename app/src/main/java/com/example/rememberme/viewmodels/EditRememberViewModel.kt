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

class EditRememberViewModel (
    private val repository: RememberRepository,
    reminderID: Long,
) : ViewModel() {

    private var _reminder: MutableLiveData<Reminder> =
        MutableLiveData(repository.filterReminder(id = reminderID).value)
    val reminder: LiveData<Reminder> = _reminder

    fun getRemindersDEBUG(reminderID: Long) {
        Log.d("editVM", "reminderID in editVM: $reminderID")
        Log.i("editVM", "_reminder in editVM: ${_reminder.value?.title}")
        Log.i("editVM", "filterReminder in editVM: ${(repository.filterReminder(id = reminderID).value?.title)}")
    }

    fun updateReminder(reminder:Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            //reminder.let { // call block only if not null
            //  if (it.text.isNotEmpty()) {  // add more "Pflichtfelder" here if necessary
            repository.updateReminder(reminder)//
            Log.d("ViewModel", "reminder updated")
            //}
            //}
        }
    }
    fun setText(text: String) {
        _reminder.value?.text = text //wenn string übergeben wird wird geschaut ob eh nicht null
    }
    fun setDate(d: Int, m: Int, y: Int) {
        _reminder.value?.d = d //wenn string übergeben wird wird geschaut ob eh nicht null
        _reminder.value?.m = m
        _reminder.value?.y = y
    }
    fun setTime(h:Int, min: Int) {
        _reminder.value?.h = h //wenn string übergeben wird wird geschaut ob eh nicht null
        _reminder.value?.min = min

    }
    fun setTitle(title: String) {
        _reminder.value?.title = title //wenn string übergeben wird wird geschaut ob eh nicht null
    }
/*
    fun getReminderbyID(reminderID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _reminder.postValue(repository.filterReminder(id = reminderID)) //postvalue instead of value because of coroutine https://stackoverflow.com/questions/51299641/difference-of-setvalue-postvalue-in-mutablelivedata?rq=1
        }
    }

 */


}