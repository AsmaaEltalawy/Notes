package com.example.notesapp.ui.edit_or_delete_note.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notesapp.R
import com.example.notesapp.data.models.Note
import com.example.notesapp.databinding.ActivityEditOrDeleteNoteBinding
import com.example.notesapp.ui.edit_or_delete_note.view_model.Update_Delete_ViewModel
import com.google.android.material.snackbar.Snackbar

class EditOrDeleteNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditOrDeleteNoteBinding

    val viewModel :Update_Delete_ViewModel by viewModels()
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_or_delete_note)


                val note = intent.getParcelableExtra<Note>("note")
                note?.let {
                    binding.etNote.setText(it.note)
                    binding.etEdittitle.setText(it.title)
                }
                binding.btEdit.setOnClickListener {
                    if(!binding.etEdittitle.text.isNullOrEmpty() &&
                        !binding.etNote.text.isNullOrEmpty()) {
                        note?.let {
                            it.title = binding.etEdittitle.text.toString()
                            it.note = binding.etNote.text.toString()
                            viewModel.updateNote(note)
                        }
                    }
                }
                binding.deleteappbar.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete-> {
                            note?.let {
                                viewModel.deleteNote(it)  // Make sure `note` is not null
                            }
                            true
                        }
                        else -> false
                    }
                }

                viewModel.editNote.observe(this, Observer {
                    binding.btEdit.hideKeyboard()
                    Snackbar.make(binding.main, R.string.Note_update_succesfully, Snackbar.LENGTH_LONG)
                        .setAction(R.string.dismiss) {
                            finish()
                        }.show()
                })
              viewModel.deleteResult.observe(this, Observer {
                  binding.deleteappbar.hideKeyboard()
                  Snackbar.make(binding.main, R.string.Note_delete_succesfully, Snackbar.LENGTH_LONG)
                      .setAction(R.string.dismiss) {
                          finish()
                      }.show()
                })

            }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}