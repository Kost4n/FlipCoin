package com.yes.or.no.app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(recordEntity: Record)

    @Query("SELECT * FROM records ORDER BY id DESC")
    fun getNotes(): Flow<List<Record>>

    @Delete
    suspend fun deleteNote(record: Record)
} 