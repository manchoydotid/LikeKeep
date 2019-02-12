package com.brownbox.likekeep

class Note(val noteId: Long?, val noteTitle: String?, val noteDesc: String?) {

    companion object {
        const val TABLE_NOTE: String = "TABLE_NOTE"
        const val ID: String = "ID"
        const val TITLE: String = "TITLE"
        const val DESC: String = "DESC"
    }
}