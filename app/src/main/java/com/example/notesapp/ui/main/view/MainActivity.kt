package com.example.notesapp.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.notesapp.data.models.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.ui.add_note.view.AddNoteActivity
import com.example.notesapp.ui.main.adapter.NotesAdapter
import com.example.notesapp.ui.main.view_model.MainViewModel

class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding
    val notesList: MutableList<Note> = mutableListOf()
    lateinit var notesAdapter: NotesAdapter

    val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()

        viewModel.notes.observe(this, Observer {
            notesAdapter = NotesAdapter(it)
            for(i in it.indices) println(it.get(i).id)
            binding.rvNotes.adapter = notesAdapter
        })
    }
}
