package com.cm.media.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VodHistory(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "vid") val vid: String,
    @ColumnInfo(name = "duration") var duration: Long,
    @ColumnInfo(name = "position") var position: Long,
    @ColumnInfo(name = "episode") var episode: Int,
    @ColumnInfo(name = "epiCount") var epiCount: Int
)