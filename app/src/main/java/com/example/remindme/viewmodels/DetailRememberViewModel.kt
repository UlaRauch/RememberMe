package com.example.remindme.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.remindme.models.Reminder
import com.example.remindme.repositories.RememberRepository
import com.example.remindme.utils.RememberWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRememberViewModel(
    private val repository: RememberRepository,
    private val workManager: WorkManager,
    private val reminderID: Long
) : ViewModel() {

    val reminder: LiveData<Reminder> = repository.filterReminder(id = reminderID)

    fun getRemindersDEBUG() {
        //Log.i("edit", "_reminder: ${_reminder.value}")
        Log.i("edit", "reminder in detailVM: ${reminder.value?.title}")
    }

    fun removeReminder(reminder: Reminder, tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReminder(reminder)
        }
        //Log.i("Delete Detail", "deleting work with tag: $tag")
        workManager.cancelAllWorkByTag(tag)
        //val workinfo = workManager.getWorkInfosByTag("5").toString()
        //Log.i("Delete Detail", "get info for for work nr $tag: $workinfo")
    }

}