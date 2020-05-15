package com.motasem.ziad.mynotes.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.motasem.ziad.mynotes.model.Note

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val db: SQLiteDatabase = this.writableDatabase

    companion object {
        val DATABASE_NAME = "My Notes"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Note.TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists ${Note.TABLE_NAME}")
    }


    fun insertNote(title: String, note: String): Boolean {
        val cv = ContentValues()
        cv.put(Note.COL_TITLE, title)
        cv.put(Note.COL_NOTE, note)
        return db.insert(Note.TABLE_NAME, null, cv) > 0
    }

    fun getAllNotes(): ArrayList<Note> {
        val data = ArrayList<Note>()
        val c =
            db.rawQuery(
                "select * from ${Note.TABLE_NAME} order by ${Note.COL_ID} desc",
                null
            )
        c.moveToFirst()
        while (!c.isAfterLast) {
            val note =
                Note(c.getInt(0), c.getString(1), c.getString(2))
            data.add(note)
            c.moveToNext()
        }
        c.close()
        return data
    }

    fun deleteNote(id: Int): Boolean {
        return db.delete(Note.TABLE_NAME, "${Note.COL_ID} = $id", null) > 0
    }

    fun updateNote(oldId: Int, title: String?, note: String?): Boolean {
        val cv = ContentValues()
        //if(name!=null)
        cv.put(Note.COL_TITLE, title)
        cv.put(Note.COL_NOTE, note)
        return db.update(Note.TABLE_NAME, cv, "${Note.COL_ID} = $oldId", null) > 0
    }
}