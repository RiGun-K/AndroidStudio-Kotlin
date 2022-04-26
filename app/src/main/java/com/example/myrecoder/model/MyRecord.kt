package com.example.myrecoder.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyRecord (
    @PrimaryKey(autoGenerate = true) val rid:Int,
    @ColumnInfo val food:String,
    @ColumnInfo val time:String
)