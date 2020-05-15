package com.motasem.ziad.mynotes

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.motasem.ziad.mynotes.adapter.NoteAdapter
import com.motasem.ziad.mynotes.db.DatabaseHelper
import com.motasem.ziad.mynotes.model.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NoteAdapter.OnClickItem {
    lateinit var list: ArrayList<Note>
    lateinit var db: DatabaseHelper
    var isState: Boolean = false
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        db = DatabaseHelper(this)
        list = db.getAllNotes()

        rvNotes.layoutManager = LinearLayoutManager(this)
        // rvNotes.layoutManager = GridLayoutManager(this, 2)
        rvNotes.setHasFixedSize(true)

        noteAdapter = NoteAdapter(this, list, this)
        rvNotes.adapter = noteAdapter


        edSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })

        fabAdd.setOnClickListener {
            val i = Intent(this, AddNoteActivity::class.java)
            startActivity(i)
        }


    }


    private fun filter(text: String) {
        val filteredList = ArrayList<Note>()
        for (i in filteredList) {
            if (i.title!!.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(i)
            }
        }
        noteAdapter.filterList(filteredList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)


        /*   var item: MenuItem = menu!!.findItem(R.id.search)
           val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
           searchView = menu.findItem(R.id.search).actionView as SearchView
           searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
           searchView.maxWidth = Int.MAX_VALUE
           searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String?): Boolean {
                   adapter1.filter.filter(query)
                   return false
               }

               override fun onQueryTextChange(newText: String?): Boolean {
                   adapter1.filter.filter(newText)
                   return false
               }

           })



         */
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Moatasem Z. AbuNeama")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            R.id.contact -> {
                val emailIntent =
                    Intent(
                        Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", "my_notes_2020@gmail.com", null)
                    )

                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            }
            R.id.about -> {
                val i = Intent(this, AboutActivity::class.java)
                startActivity(i)
            }
            R.id.privacy -> {
                val i = Intent(this, PrivacyPolicyActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(position: Int) {
        val i = Intent(this, AddNoteActivity::class.java)
        i.putExtra("id", list[position].id)
        i.putExtra("title", list[position].title)
        i.putExtra("note", list[position].note)
        startActivity(i)
    }

    override fun onDelete(position: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Are you sure you want to Delete this note?")
        alertDialog.setCancelable(false)
        alertDialog.setIcon(R.drawable.ic_delete)

        alertDialog.setPositiveButton("Yes") { _, _ ->
            if (db.deleteNote(position)) {
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        alertDialog.setNegativeButton("No") { _, _ ->
        }
        alertDialog.create().show()
    }

    fun convertDemo(item: MenuItem) {
        if (!isState) {
            item.setIcon(R.drawable.ic_menu)
            rvNotes.layoutManager = GridLayoutManager(this, 2)
            isState = true
        } else {
            item.setIcon(R.drawable.ic_apps)
            rvNotes.layoutManager = LinearLayoutManager(this)
            isState = false
        }
    }


}
