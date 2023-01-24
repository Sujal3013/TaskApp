package com.example.taskdone.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskdone.modal.TodoModel

@Dao
interface tododao {

    @Insert()
    suspend fun insertTask(todoModel: TodoModel):Long

    @Query("Select * from TodoModel where isFinished!=-1")
    fun getTask():LiveData<List<TodoModel>>

    @Query("Update TodoModel Set isFinished=1 where id=:uid")
    fun finishedTask(uid:Long)

    @Query("Delete from TodoModel where id=:uid")
    fun deleteTask(uid:Long)
}