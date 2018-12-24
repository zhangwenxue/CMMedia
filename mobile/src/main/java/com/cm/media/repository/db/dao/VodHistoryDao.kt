package com.cm.media.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cm.media.repository.db.entity.VodHistory
import io.reactivex.Flowable

@Dao
interface VodHistoryDao {
    @Query("SELECT * FROM vodhistory")
    fun listHistories(): Flowable<List<VodHistory>>

    @Query("SELECT * FROM vodhistory WHERE vid = :id LIMIT 1")
    fun findByVId(id: String): Flowable<VodHistory>

    @Insert
    fun insert(history: VodHistory)

    @Update
    fun update(history: VodHistory)
}