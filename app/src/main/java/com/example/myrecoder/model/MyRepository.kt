package com.example.myrecoder.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MyRepository(private val myDao: MyDao) {
    val records: Flow<List<MyRecord>> = myDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(record: MyRecord) {
        myDao.insert(record)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(record: MyRecord) {
        myDao.delete(record)
    }

}