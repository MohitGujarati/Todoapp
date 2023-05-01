package com.example.todoapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.example.todoapp.database.CatDb
import com.example.todoapp.database.NotesDb
import com.example.todoapp.databinding.FragmentAddNotesFrgBinding
import com.example.todoapp.model.NoteModel

class AddNotesFrg : Fragment() {
    private var _binding: FragmentAddNotesFrgBinding? = null
    private val binding get() = _binding!!
    var priority = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNotesFrgBinding.inflate(inflater, container, false)


        binding.apply {
            rd1.setOnClickListener { priority=1 }
            rd2.setOnClickListener { priority=2 }
            rd3.setOnClickListener { priority=3 }
        }

        binding.btnSave.setOnClickListener {

            var title = binding.tvTitle.text.toString()
            var note = binding.tvSubtitle.text.toString()
            var categeory = binding.customerTextView.text.toString()
            var priority = priority


            if (title.isBlank()) {
                binding.tvTitle.setError("Add title")
            }
            if (note.isBlank()) {
                binding.tvSubtitle.setError("Add note")
            }
            if (categeory.isBlank()) {
                binding.customerTextView.setError("Add categeory")
            }

            if ((title.isBlank() && note.isBlank() && categeory.isBlank()) == false) {

                var dbhelper = NotesDb(binding.root.context)

                dbhelper.insert(
                    NoteModel(
                        it.id,title, note, categeory, priority
                    )
                )
                Toast.makeText(binding.root.context, "Saved", Toast.LENGTH_SHORT).show()

                Log.d("inputdata", "$title $note $categeory $priority")
            }


        }

        return binding.root
    }



}