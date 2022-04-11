package com.reihan.noteapp.data.room

import android.content.Context
import androidx.room.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NoteDB: RoomDatabase() {
    abstract fun noteDao(): NoteDAO

    companion object {
        @Volatile
        var instance: NoteDB? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteDB{
            if(instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        NoteDB::class.java,
                        "note_database"
                    ).build()
                }
            }
            return instance as NoteDB
        }
    }
}