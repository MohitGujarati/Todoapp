package com.example.todoapp.database

import android.annotation.SuppressLint
import android.app.DownloadManager.Query
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.todoapp.model.NoteModel

class NotesDb(var context: Context) : SQLiteOpenHelper(context, TABLE_NAME, null, DB_version) {

    companion object {
        var TABLE_NAME = "Notes_Table"
        var DB_version = 1
        var userid = "key_id"
        var id_tile = "Title"
        var id_note = "Notes"
        var id_cat = "Categeory"
        var id_priority = "Priority"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            (
                    "CREATE TABLE " + TABLE_NAME.toString() + " "
                            + " ( " + userid.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                            + id_tile.toString() + " TEXT,"
                            + id_note.toString() + " TEXT,"
                            + id_cat.toString() + " TEXT,"
                            + id_priority.toString() + " INTEGER)"
                    )
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    fun insert(modelclass: NoteModel): Long {

        var note_db = this.writableDatabase

        var note_cv = ContentValues()


        note_cv.put(id_tile, modelclass.Title)
        note_cv.put(id_note, modelclass.Note)
        note_cv.put(id_cat, modelclass.Category)
        note_cv.put(id_priority, modelclass.Priority)


        var insert = note_db.insert(TABLE_NAME, null, note_cv)
        return insert


    }

    @SuppressLint("Range")
    fun retrieve(num: Int): ArrayList<NoteModel> {

        var userdatalist: MutableList<NoteModel> = ArrayList<NoteModel>()

        var query: String = ""


        when (num) {
            0 -> query = "select * from $TABLE_NAME order by $userid desc"
            1 -> query = "select * from $TABLE_NAME where $id_priority = $num order by $userid desc"
            2 -> query = "select * from $TABLE_NAME where $id_priority = $num order by $userid desc"
            3 -> query = "select * from $TABLE_NAME where $id_priority = $num order by $userid desc"
            4 -> query = "select * from $TABLE_NAME order by $id_priority desc"
            5 -> query = "select * from $TABLE_NAME order by $id_priority asc"
        }


        var cursor: Cursor?
        var notedatabase = this.writableDatabase


        try {
            cursor = notedatabase.rawQuery(query, null)
        } catch (Exp: SQLiteException) {
            notedatabase.execSQL(query)
            return ArrayList()
        }


        var iduser:Int
        var titleid: String
        var noteid: String
        var categoryid: String
        var priorityid: Int


        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {

                do {

                    iduser = cursor.getInt(cursor.getColumnIndex(userid))
                    titleid = cursor.getString(cursor.getColumnIndex(id_tile))
                    noteid = cursor.getString(cursor.getColumnIndex(id_note))
                    categoryid = cursor.getString(cursor.getColumnIndex(id_cat))
                    priorityid = cursor.getInt(cursor.getColumnIndex(id_priority))


                    var userdatas =
                        NoteModel(iduser,titleid, noteid, categoryid, priorityid)
                    userdatalist.add(userdatas)


                } while (cursor.moveToNext())
            }
        }

        return userdatalist as ArrayList<NoteModel> /* = java.util.ArrayList<mohit.dev.interviewprep1.Model.Model_History> */
    }

    fun update(mymodel: NoteModel): Int {
        var db = this.writableDatabase
        var cv_update = ContentValues()


        cv_update.put(id_tile, mymodel.Title)
        cv_update.put(id_note, mymodel.Note)
        cv_update.put(id_cat, mymodel.Category)
        cv_update.put(id_priority, mymodel.Priority)


        var update = db.update(TABLE_NAME, cv_update, userid + "=" + mymodel.Userid, null)
        db.close()

        return update

    }


    fun note_delete(mymodel: NoteModel): Int {
        var database = this.writableDatabase
        var note_delete = database.delete(TABLE_NAME, userid + "=" + mymodel.Userid, null)
        return note_delete
    }


}