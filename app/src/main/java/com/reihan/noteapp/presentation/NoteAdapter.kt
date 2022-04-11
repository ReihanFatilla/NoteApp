package com.reihan.noteapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reihan.noteapp.R
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.data.room.Priority
import com.reihan.noteapp.databinding.ItemNoteBinding
import com.reihan.noteapp.presentation.home.HomeFragmentDirections
import com.reihan.noteapp.utils.DiffCallback

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.MyViweHolder>() {

    var listNote = ArrayList<Note>()

    class MyViweHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MyViweHolder (
        ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

    override fun onBindViewHolder(holder: MyViweHolder, position: Int) {
        val note = listNote[position]
        holder.binding.apply{
            mNote = note
            executePendingBindings()
        }
    }

    override fun getItemCount() = listNote.size

    fun setData(newList: List<Note>?) {
        if (newList == null) return
        val notesDiffUtil = DiffCallback(listNote, newList)
        val notesDiffutilResult = DiffUtil.calculateDiff(notesDiffUtil)
        listNote.clear()
        listNote.addAll(newList)
        notesDiffutilResult.dispatchUpdatesTo(this)
    }
}