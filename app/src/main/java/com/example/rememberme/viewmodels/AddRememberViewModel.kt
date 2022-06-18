package com.example.rememberme.viewmodels

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.rememberme.models.Reminder
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.utils.RememberWorker
import com.example.rememberme.utils.WorkRequestUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AddRememberViewModel(
    private val repository: RememberRepository,
    private val workManager: WorkManager,
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

    fun addReminder() { //brauche keinen parameter weil alles im viewmodel is

        viewModelScope.launch(Dispatchers.IO) {
            reminder.value?.let { // call block only if not null
                /*
                ab hier geändert oder neu von Ula
                 */
                if (it.title.isNotEmpty()) {  // add more "Pflichtfelder" here if necessary
                    _reminder.value?.id = repository.addReminder(reminder.value!!)// insert new reminder an get the new id
                    //_id.postValue(tempID) //takes too long, old value will be written to workrequest
                    Log.d("Delete AddVM", "reminder added: id = ${reminder.value}")
                    WorkRequestUtils.createWorkRequest(reminder.value!!, workManager = workManager)
                    //Log.d("ViewModel", "workrequest for: Month: ${reminder.value?.m} (calendar index), Day: ${reminder.value?.d}:")
                }
            }
        }
    }
            /*
            Ende Ula
             */
}


