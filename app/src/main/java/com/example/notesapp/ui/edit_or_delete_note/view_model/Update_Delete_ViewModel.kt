package com.example.notesapp.ui.edit_or_delete_note.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.local.AppDatabase
import com.example.notesapp.data.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Update_Delete_ViewModel(val app: Application) : AndroidViewModel(app) {

    val _editNote: MutableLiveData<Unit> = MutableLiveData()
    val editNote :LiveData<Unit> = _editNote

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> = _deleteResult

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(app.applicationContext).noteDao()

            val result = noteDao.updateNote(note)
            withContext(Dispatchers.Main){
                _editNote.postValue(result)
            }
        }
    }
    fun deleteNote(note: Note) {
        viewModelScope.launch(IO) {
            val noteDao = AppDatabase.DatabaseBuilder.getInstance(getApplication()).noteDao()
            val numDeletedRows = noteDao.deleteNote(note)
            withContext(Dispatchers.Main) {
                _deleteResult.postValue(numDeletedRows>1)

            }
        }
    }
}