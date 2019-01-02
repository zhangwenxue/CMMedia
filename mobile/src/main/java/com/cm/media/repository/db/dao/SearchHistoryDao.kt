package com.cm.media.repository.db.dao

import android.database.Cursor
import androidx.room.*
import com.cm.media.repository.db.entity.SearchHistory

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM SearchHistory WHERE value LIKE '%'||:key||'%'")
    fun getHistoryCursor(key: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: SearchHistory)

    @Delete
    fun delete(history: SearchHistory)
}