package com.brownbox.likekeep

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context)
    : ManagedSQLiteOpenHelper(ctx, "note.db", null, 1){

    companion object {
        private var instance: DBHelper? = null
        @Synchronized
        fun getInstance(ctx: Context) : DBHelper{
            if(instance == null){
                instance = DBHelper(ctx.applicationContext)
            }
            return instance as DBHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Note.TABLE_NOTE, true,
            Note.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Note.TITLE to TEXT,
            Note.DESC to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Note.TABLE_NOTE, true)
    }
}

val Context.database : DBHelper
get() = DBHelper.getInstance(applicationContext)