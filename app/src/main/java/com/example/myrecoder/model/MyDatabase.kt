package com.example.myrecoder.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyRecord::class], version = 1)
abstract class MyDatabase:RoomDatabase() {
    abstract fun myDao():MyDao

    companion object{
        private var INSTANCE:MyDatabase?=null
        @JvmStatic
        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}