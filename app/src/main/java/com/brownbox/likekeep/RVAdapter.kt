package com.brownbox.likekeep

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RVAdapter(val context: Context, val items: ArrayList<Note>, private val listener: (Note) -> Unit)
    : RecyclerView.Adapter<RVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(items[p1], listener)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItem(items: Note, listener: (Note) -> Unit){
            itemView.tv_title.text = items.noteTitle
            itemView.tv_desc.text = items.noteDesc

            itemView.setOnClickListener {
                listener(items)
            }

            itemView.setOnLongClickListener {
                itemView.context.toast("Long Click")
                return@setOnLongClickListener true
            }

//            itemView.iv_edit.setOnClickListener {
//                //Intent bawa data ke AddNoteActivity
//                itemView.context.startActivity<AddNoteActivity>(
//                    "oldTitle" to items.noteTitle,
//                    "oldDesc" to items.noteDesc
//                )
//            }
//
//            itemView.iv_delete.setOnClickListener {
//                itemView.context.database.use {
//                    delete(Note.TABLE_NOTE, "(${Note.TITLE} = {title})",
//                        "title" to items.noteTitle.toString())
//                }
//                itemView.context.toast("Data dihapus")
//            }
        }

    }


}