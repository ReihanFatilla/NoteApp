package com.reihan.noteapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.reihan.noteapp.data.room.Note

class DiffCallback(private val oldList: List<Note>, private val newList: List<Note>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]
        return oldData.id == newData.id &&
                oldData.date == newData.date  &&
                oldData.desc == newData.desc &&
                oldData.title == newData.title &&
                oldData.priority == newData.priority
    }

}