package com.yes.or.no.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yes.or.no.app.db.RecordDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import com.yes.or.no.app.db.Record

class RecViewModel(application: Application): AndroidViewModel(application) {
    private val notesDao by lazy {
        RecordDb.getDatabase(application.applicationContext).getRecordDao()
    }

    fun addRecord(text: String, answer: String) = viewModelScope.launch(Dispatchers.IO) {
        notesDao.addNote(Record(text, answer ,getDate()))
    }

    fun deleteRecord(record: Record) = viewModelScope.launch(Dispatchers.IO) {
        notesDao.deleteNote(record)
    }

    private val sdf = SimpleDateFormat("dd/M/yyyy")

    private fun getDate(): String = sdf.format(Date())
}