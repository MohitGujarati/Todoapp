package com.example.todoapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todoapp.database.NotesDb
import com.example.todoapp.databinding.FragmentUpdateNoteBinding
import com.example.todoapp.model.NoteModel

class UpdateNoteFrag : Fragment() {

    private var upfrag_binding: FragmentUpdateNoteBinding? = null
    private val binding get() = upfrag_binding!!

    var title: String = ""
    var note = ""
    var categoryies = ""
    var userid: Int = 0
    var priority: Int = 0
    var position: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        upfrag_binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        var bundle = arguments
        title = bundle?.getString("title").toString()
        note = bundle?.getString("note").toString()
        categoryies = bundle?.getString("category").toString()
        userid = bundle?.getInt("userid")!!.toInt()
        priority = bundle?.getInt("priority")!!.toInt()
        position = bundle?.getInt("position")!!.toInt()
        Log.d("mytexview", title.toString())

        refreshdata()

        var radiobtn = 0

        binding.apply {
            rd1.setOnClickListener { radiobtn = 1 }
            rd2.setOnClickListener { radiobtn = 2 }
            rd3.setOnClickListener { radiobtn = 3 }
        }
        binding.btnupdateSave.setOnClickListener {


            var db_updateHelper = NotesDb(binding.root.context)

            db_updateHelper.update(
                NoteModel(
                    userid,
                    binding.edTitle.text.toString(),
                    binding.edNote.text.toString(),
                    binding.edCat.text.toString(),
                    radiobtn
                )
            )
            Toast.makeText(
                binding.root.context,
                "Updated",
                Toast.LENGTH_SHORT
            ).show()
        }




        return binding.root
    }

    private fun refreshdata() {
        binding.edTitle.setText(title.toString())
        binding.edNote.setText(note.toString())
        binding.edCat.setText(categoryies.toString())

        binding.apply {
            when (priority) {
                1 -> rd1.isChecked = true
                2 -> rd2.isChecked = true
                3 -> rd3.isChecked = true
            }
        }
    }


}