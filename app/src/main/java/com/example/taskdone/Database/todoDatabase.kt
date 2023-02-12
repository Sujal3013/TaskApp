package com.example.taskdone.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskdone.DB_NAME
import com.example.taskdone.Dao.tododao
import com.example.taskdone.modal.TodoModel


@Database(entities = [TodoModel::class],version=1)
abstract class todoDatabase :RoomDatabase(){
    abstract fun todoDao():tododao

    companion object{
        //Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private  var INSTANCE:todoDatabase?=null

        fun getDatabase(context: Context):todoDatabase{
            val tempinstance= INSTANCE
            if(tempinstance!=null){
                return tempinstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    todoDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE =instance
                return instance

            }
        }
    }
}