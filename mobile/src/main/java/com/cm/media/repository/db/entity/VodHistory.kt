package com.cm.media.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VodHistory(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "vid") val vid: String,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "season") var season: String = "",
    @ColumnInfo(name = "post") var post: String = "",
    @ColumnInfo(name = "vodType") var vodType: Int = 0,
    @ColumnInfo(name = "duration") var duration: Long = 0,
    @ColumnInfo(name = "position") var position: Long = 0,
    @ColumnInfo(name = "episode") var episode: Int = 1,
    @ColumnInfo(name = "epiCount") var epiCount: Int = 0,
    @ColumnInfo(name = "modifiedTime") var modifiedTime: Long = 0
) {
    companion object {
        fun createVodHistory(vid: String): VodHistory {
            return VodHistory(vid = vid, modifiedTime = System.currentTimeMillis())
        }
    }
}