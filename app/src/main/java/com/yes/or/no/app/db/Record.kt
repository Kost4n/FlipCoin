package com.yes.or.no.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @ColumnInfo(name = "record_text") val recordText: String,
    @ColumnInfo(name = "record_date") val recordDate: String,
    @ColumnInfo(name = "record_answer") val recordAnswer: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}