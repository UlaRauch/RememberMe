package com.example.rememberme.viewmodels

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
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class EditRememberViewModel (
    private val repository: RememberRepository,
    private val workManager: WorkManager,
    reminderID: Long
) : ViewModel() {
    val reminder: LiveData<Reminder> = repository.filterReminder(id = reminderID)
    private var _updatedReminder: Reminder? = reminder.value

    fun initializeReminder(){
        _updatedReminder = reminder.value
    }

    fun updateReminder() {
        _updatedReminder?.let { reminder ->
            viewModelScope.launch(Dispatchers.IO) {
                //reminder.let { // call block only if not null
                //  if (it.text.isNotEmpty()) {  // add more "Pflichtfelder" here if necessary
                repository.updateReminder(reminder = reminder)//
                //Log.d("ViewModel", "reminder updated")
                workManager.cancelAllWorkByTag(reminder.id.toString())
                WorkRequestUtils.createWorkRequest(reminder, workManager)
                //Log.d("ViewModel", "workrequest for: Month: ${reminder.m}(calendar index), Day: ${reminder.d}:")
                //}
                //}
            }
        }
    }
    fun setText(text: String) {
        _updatedReminder?.text = text //wenn string übergeben wird wird geschaut ob eh nicht null
    }
    fun setDate(d: Int, m: Int, y: Int) {
        _updatedReminder?.d = d //wenn string übergeben wird wird geschaut ob eh nicht null
        _updatedReminder?.m = m
        _updatedReminder?.y = y
    }
    fun setTime(h:Int, min: Int) {
        _updatedReminder?.h = h //wenn string übergeben wird wird geschaut ob eh nicht null
        _updatedReminder?.min = min

    }
    fun setTitle(title: String) {
        _updatedReminder?.title = title //wenn string übergeben wird wird geschaut ob eh nicht null
    }

    /*
    fun getReminderbyID(reminderID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _updatedReminder.postValue(repository.filterReminder(id = reminderID).value) //postvalue instead of value because of coroutine https://stackoverflow.com/questions/51299641/difference-of-setvalue-postvalue-in-mutablelivedata?rq=1
        }
    }

     */

}