package com.motasem.ziad.mynotes.model

import android.os.Parcel
import android.os.Parcelable

data class Note(var id: Int, var title: String?, var note: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }

        val COL_ID = "id"
        val COL_TITLE = "title"
        val COL_NOTE = "note"

        val TABLE_NAME = "Note"
        val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID integer primary key autoincrement," +
                "$COL_TITLE text not null, $COL_NOTE text)"
    }
}