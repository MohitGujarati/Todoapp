package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.NoteModel
import kotlinx.coroutines.delay

class notesAdapter(
    var context: Context,
    var datalist: ArrayList<NoteModel>,
    var onClick: OnclickCardview
) :
    RecyclerView.Adapter<notesAdapter.ViewHolder>() {

    interface OnclickCardview {
        fun Onclickbtn(
            position: Int,
            userid: Int,
            title: String,
            note: String,
            category: String,
            priority: Int
        )

        fun onclikitem(
            position: Int,
            userid: Int,
            title: String,
            note: String,
            category: String,
            priority: Int
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_recview, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var mymodel = datalist[position]


        holder.apply {
            rec_title.text = mymodel.Title
            rec_note.text = mymodel.Note
            rec_category.text = mymodel.Category
        }




        holder.card_note.setOnClickListener {
            onClick.onclikitem(
                position,
                mymodel.Userid,
                mymodel.Title,
                mymodel.Note,
                mymodel.Category,
                mymodel.Priority
            )
        }

        holder.checkbox.setOnClickListener {

            if (holder.checkbox.isChecked) {

                onClick.Onclickbtn(
                    position,
                    mymodel.Userid,
                    mymodel.Title,
                    mymodel.Note,
                    mymodel.Category,
                    mymodel.Priority
                )
                Handler().postDelayed({
                    holder.checkbox.isChecked=false
                }, 3000)

            }



        }

        when (mymodel.Priority.toString()) {
            "1" -> holder.imgview.setBackgroundColor(Color.parseColor("#39FF14"));
            "2" -> holder.imgview.setBackgroundColor(Color.parseColor("#00008B"))
            "3" -> holder.imgview.setBackgroundColor(Color.parseColor("#FF0000"))
        }


    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var rec_title = itemView.findViewById<TextView>(R.id.rec_title)
        var rec_note = itemView.findViewById<TextView>(R.id.rec_note)
        var rec_category = itemView.findViewById<TextView>(R.id.rec_category)
        var imgview = itemView.findViewById<LinearLayout>(R.id.imgview)
        var card_note = itemView.findViewById<CardView>(R.id.card_note)
        var checkbox = itemView.findViewById<CheckBox>(R.id.rec_check)

    }
}