package com.motasem.ziad.mynotes.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.motasem.ziad.mynotes.R
import com.motasem.ziad.mynotes.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(var context: Context, var data: ArrayList<Note>, var click: OnClickItem) :
    RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.tvTitle
        val tvNote: TextView = itemView.tvNote
        val card: CardView = itemView.card

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = data[position].title
        holder.tvNote.text = data[position].note
        holder.card.setOnLongClickListener {
            click.onDelete(holder.adapterPosition)
            true
        }
        holder.card.setOnClickListener {
            click.onClick(holder.adapterPosition)
        }

    }

    fun  filterList(filteredList: ArrayList<Note>) {
        data = filteredList
        notifyDataSetChanged()
    }


    interface OnClickItem {
        fun onClick(position: Int)
        fun onDelete(position: Int)
    }
}