package com.example.myrecoder

import android.app.Application
import com.example.myrecoder.model.MyDatabase
import com.example.myrecoder.model.MyRepository


class MyRecordApplication : Application() {
    val database by lazy { MyDatabase.getInstance(this) }
    val repository by lazy { MyRepository(database.myDao()) }
}