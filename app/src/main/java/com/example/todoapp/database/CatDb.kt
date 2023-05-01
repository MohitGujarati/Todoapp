package com.example.todoapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.todoapp.model.CatModel
import com.example.todoapp.model.NoteModel

class CatDb(var context: Context) : SQLiteOpenHelper(context, TABLE_NAME, null, DB_version) {

    companion object {
        var TABLE_NAME = "Cat_Table"
        var DB_version = 1
        var user_cat_id="userid"
        var user_cat = "categeory"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            (
                    "CREATE TABLE " + TABLE_NAME.toString() + " "
                            + " ( " + user_cat_id.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                            + user_cat.toString() + " TEXT)"
                    )
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    fun insert_cat(modelclass: CatModel): Long {

        var note_db = this.writableDatabase

        var note_cv = ContentValues()


        note_cv.put(user_cat, modelclass.catname)


        var insert = note_db.insert(TABLE_NAME, null, note_cv)
        return insert


    }

    @SuppressLint("Range")
    fun retrieve_cat(): ArrayList<CatModel> {

        var userdatalist: MutableList<CatModel> = ArrayList<CatModel>()


        var query = "select * from $TABLE_NAME order by $user_cat_id desc"


        var cursor: Cursor?
        var notedatabase = this.writableDatabase


        try {
            cursor = notedatabase.rawQuery(query, null)
        } catch (Exp: SQLiteException) {
            notedatabase.execSQL(query)
            return ArrayList()
        }

        var catid:Int
        var categoryid: String



        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {

                do {
                    catid = cursor.getInt(cursor.getColumnIndex(user_cat_id))
                    categoryid = cursor.getString(cursor.getColumnIndex(user_cat))


                    var userdatas =
                        CatModel(catid,categoryid)
                    userdatalist.add(userdatas)


                } while (cursor.moveToNext())
            }
        }

        return userdatalist as ArrayList<CatModel> /* = java.util.ArrayList<mohit.dev.interviewprep1.Model.Model_History> */
    }
}