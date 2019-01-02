package com.cm.media.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "value") var value: String = ""
) {
    companion object {
        fun newHistory(name: String): SearchHistory {
            return SearchHistory(value = name)
        }
    }
}