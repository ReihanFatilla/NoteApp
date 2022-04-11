package com.reihan.noteapp.data.room

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromPriority(priorityString: Priority): String{
        return priorityString.toString()
    }

    @TypeConverter
    fun toPriority(priority: String): Priority{
        return Priority.valueOf(priority)
    }
}