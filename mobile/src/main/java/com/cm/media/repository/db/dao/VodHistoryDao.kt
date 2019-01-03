package com.cm.media.repository.db.dao

import androidx.room.*
import com.cm.media.repository.db.entity.VodHistory
import io.reactivex.Flowable

@Dao
interface VodHistoryDao {
    @Query("SELECT * FROM VodHistory ORDER BY modifiedTime DESC")
    fun listHistories(): Flowable<List<VodHistory>>

    @Query("SELECT * FROM VodHistory WHERE vid = :id LIMIT 1")
    fun findByVId(id: String): Flowable<List<VodHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: VodHistory)

    @Update
    fun update(history: VodHistory)

    @Delete
    fun delete(history: VodHistory)
}