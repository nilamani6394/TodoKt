package com.app.common.data.database.dao

import androidx.room.*
import com.app.common.model.RoomModel

@Dao
interface TaskDao {
    @Query("SELECT * FROM RoomModel")
    suspend fun getAll(): List<RoomModel>

    @Insert
    suspend fun insert(vararg task: RoomModel)

    @Update
    suspend fun update(vararg task: RoomModel)

    @Delete
    suspend fun delete(task: RoomModel)
}