package com.reihan.noteapp.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_note")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val date: String,
    val desc: String,
    val priority: Priority
) : Parcelable
