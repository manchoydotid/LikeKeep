package com.brownbox.likekeep

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_add_note.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.update
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast



class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle("")


        //TODO: Ini boilerplate di onCreateOptionsMenu & onOptionsItemSelected
        val noteId = intent.getStringExtra("noteId")
        val oldTitle = intent.getStringExtra("oldTitle")
        val oldDesc = intent.getStringExtra("oldDesc")

        et_title.setText(oldTitle)
        et_desc.setText(oldDesc)
        hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val noteId = intent.getStringExtra("noteId")
        val oldTitle = intent.getStringExtra("oldTitle")
        val oldDesc = intent.getStringExtra("oldDesc")

        menuInflater.inflate(R.menu.add_note_menu, menu)
        if(oldTitle.isNullOrEmpty() && oldDesc.isNullOrEmpty()){
            val delete = menu?.findItem(R.id.delete_note_menu_item) as MenuItem
            delete.setVisible(false)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val noteId = intent.getStringExtra("noteId")
        val oldTitle = intent.getStringExtra("oldTitle")
        val oldDesc = intent.getStringExtra("oldDesc")

           return when(item?.itemId){

               R.id.home -> {
                   onBackPressed()
                   true
               }
                R.id.add_note_menu_item -> {
                    if(oldTitle.isNullOrEmpty() && oldDesc.isNullOrEmpty()){
                        database.use {
                            insert(Note.TABLE_NOTE,
                                Note.TITLE to et_title.text.toString(),
                                Note.DESC to et_desc.text.toString()
                            )

                        }
                        toast("Note saved")
                    }else{
                        database.use {
                            update(Note.TABLE_NOTE,
                                Note.TITLE to et_title.text.toString(),
                                Note.DESC to et_desc.text.toString())

                                .whereArgs("${Note.ID} = {id}",
                                    "id" to noteId
                                ).exec()
                        }
                        toast("Note updated")
                    }
                    finish()
                    true
                }
                R.id.delete_note_menu_item -> {
                        database.use {
                        delete(Note.TABLE_NOTE,
                            "(${Note.ID} = {id})",
                            "id" to noteId)
                    }
                    toast("Note deleted")
                    finish()
                    true
                }
               else -> super.onOptionsItemSelected(item)
           }
    }

    //jadi saat item List di Main di klik, buka Activity gak langsung muncul keyboard pas di Edittext
    private fun hideKeyboard(){
        val view = this.currentFocus
        if(view != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

}
