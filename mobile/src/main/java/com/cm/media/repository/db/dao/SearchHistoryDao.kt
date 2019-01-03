package com.cm.media.repository.db.dao

import android.database.Cursor
import androidx.room.*
import com.cm.media.repository.db.entity.SearchHistory
import com.cm.media.repository.db.entity.VodHistory
import io.reactivex.Flowable

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM SearchHistory WHERE value LIKE '%'||:key||'%'")
    fun getHistoryCursor(key: String): Cursor

    @Query("SELECT * FROM SearchHistory ORDER BY lastModified DESC")
    fun getSearchHistoryList(): Flowable<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: SearchHistory)


    @Query("SELECT * FROM SearchHistory WHERE value = :value LIMIT 1")
    fun findByValue(value: String): List<SearchHistory>


    @Update
    fun update(history: SearchHistory)

    @Delete
    fun delete(history: SearchHistory)
}