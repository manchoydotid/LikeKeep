package com.brownbox.likekeep

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter:RVAdapter
    private var note = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RVAdapter(this, note){
            startActivity<AddNoteActivity>(
                "noteId" to "${it.noteId}",
                "oldTitle" to "${it.noteTitle}",
                "oldDesc" to "${it.noteDesc}"
            )
        }

        rv_main.adapter = adapter

        getData()
        rv_main.layoutManager = LinearLayoutManager(this)

        btn_add_note.setOnClickListener {
            startActivity<AddNoteActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        Handler().post(kotlinx.coroutines.Runnable {
            run {  }
        })

        return super.onCreateOptionsMenu(menu)
    }


    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData() {
        database.use {
            note.clear()
            val result = select(Note.TABLE_NOTE)
            val dataNote = result.parseList(classParser<Note>())
            note.addAll(dataNote)
            adapter.notifyDataSetChanged()
        }
    }
}
