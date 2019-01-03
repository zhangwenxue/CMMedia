package com.cm.media.repository.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "lastModified") var lastModified: Long = 0
) {
    companion object {
        fun newHistory(name: String): SearchHistory {
            return SearchHistory(value = name, lastModified = System.currentTimeMillis())
        }
    }
}