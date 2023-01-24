package com.example.taskdone.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskdone.Dao.tododao
import com.example.taskdone.modal.TodoModel


@Database(entities = [TodoModel::class],version=1)
abstract class todoDatabase :RoomDatabase(){
    abstract fun todoDao():tododao
}