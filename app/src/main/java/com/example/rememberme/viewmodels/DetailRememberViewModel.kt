package com.example.rememberme.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.rememberme.models.Reminder
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.utils.RememberWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRememberViewModel(
    private val repository: RememberRepository,
    private val context: Context, //TODO: This field leaks a context object - was ist das Problem?
    private val reminderID: Long
) : ViewModel() {

    val reminder: LiveData<Reminder> = repository.filterReminder(id = reminderID)

    /*
    private var _reminder: MutableLiveData<Reminder?> =
        MutableLiveData(Reminder(title = "", d = 0, m = 0, y = 0, h = 0, min = 0, text = ""))
    val reminder: LiveData<Reminder?> = _reminder

    fun getReminderbyID(reminderID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _reminder.postValue(repository.filterReminder(id = reminderID)) //postvalue instead of value because of coroutine https://stackoverflow.com/questions/51299641/difference-of-setvalue-postvalue-in-mutablelivedata?rq=1
        }
    }
*/

    fun removeReminder(reminder: Reminder, tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReminder(reminder)
        }
        Log.i("Delete Detail", "deleting work with tag: $tag")
        WorkManager.getInstance(context).cancelAllWorkByTag(tag) //TODO: funktioniert nicht...
        val workinfo = WorkManager.getInstance(context).getWorkInfosByTag("5").toString()
        Log.i("Delete Detail", "get info for for work nr $tag: $workinfo")
    }

}