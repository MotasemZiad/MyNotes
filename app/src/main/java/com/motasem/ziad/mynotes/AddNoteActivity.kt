package com.motasem.ziad.mynotes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.motasem.ziad.mynotes.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        id = intent.getIntExtra("id", -1)
        if (id != 0) {
            edTitle.setText(intent.getStringExtra("title"))
            edNote.setText(intent.getStringExtra("note"))
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        db = DatabaseHelper(this)
        if (item.itemId == R.id.addNote) {
            if (id != 0) {
                // Edit
                if (edTitle.text.isNotEmpty() && edNote.text.isNotEmpty()) {
                    if (db.updateNote(id, edTitle.text.toString(), edNote.text.toString())) {
                        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                        edTitle.text.clear()
                        edNote.text.clear()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Please Fill Fields!", Toast.LENGTH_SHORT).show()
                }
            }
            if (edTitle.text.isNotEmpty() && edNote.text.isNotEmpty()) {
                if (db.insertNote(edTitle.text.toString(), edNote.text.toString())) {
                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
                    edTitle.text.clear()
                    edNote.text.clear()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Fill Fields!", Toast.LENGTH_SHORT).show()
            }
        }


        return super.onOptionsItemSelected(item)
    }
}
