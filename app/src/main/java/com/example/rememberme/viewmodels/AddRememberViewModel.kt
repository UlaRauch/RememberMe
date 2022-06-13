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
                    val tempID = repository.addReminder(reminder.value!!)// insert new reminder an get the new id
                    //_id.postValue(tempID) //takes too long, old value will be written to workrequest
                    Log.d("Delete AddVM", "reminder added: id = $tempID")

                    // calculate time interval for notification
                    val delayInSeconds = getDelayInSeconds(
                        reminder.value!!.y,
                        reminder.value!!.m -1, //months are represented as index https://developer.android.com/reference/java/util/Date.html#Date%28int,%20int,%20int,%20int,%20int,%20int%29
                        reminder.value!!.d,
                        reminder.value!!.h,
                        reminder.value!!.min,
                    )
                    createWorkRequest(
                        id = tempID,
                        title = reminder.value!!.title,
                        message = reminder.value!!.text,
                        timeDelayInSeconds = delayInSeconds)
                }
            }
        }
    }
/*
Ende Ula
 */

    /**
     * Begin code by https://dev.to/blazebrain/building-a-reminder-app-with-local-notifications-using-workmanager-api-385f
     */
    private fun createWorkRequest(id: Long, title: String, message: String, timeDelayInSeconds: Long) {
        val workRequest = OneTimeWorkRequestBuilder<RememberWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to title,
                    "message" to message
                )
            )
            .addTag(id.toString())
            .build()
        workManager.enqueue(workRequest)
        Log.i("Delete Add", "enqueuing work with tag: $id")
    }

    fun getDelayInSeconds(year: Int, month: Int, day: Int, hour: Int, min: Int): Long {
        val userDateTime = Calendar.getInstance()
        userDateTime.set(year, month, day, hour, min)
        val now = Calendar.getInstance()
        return (userDateTime.timeInMillis / 1000L) - (now.timeInMillis / 1000L)
    }
    /**
     * End
     */

}


