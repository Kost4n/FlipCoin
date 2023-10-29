package com.yes.or.no.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        Record::class
    ],
)
abstract class RecordDb : RoomDatabase() {
    abstract fun getRecordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: RecordDb? = null

        fun getDatabase(context: Context): RecordDb {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): RecordDb {
            return Room.databaseBuilder(
                context.applicationContext,
                RecordDb::class.java,
                "note_database"
            ).build()
        }
    }
}