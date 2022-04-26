package com.example.myrecoder

import androidx.lifecycle.*
import com.example.myrecoder.model.MyRecord
import com.example.myrecoder.model.MyRepository
import kotlinx.coroutines.*


class MainViewModel(private val repository: MyRepository): ViewModel() {
    val records: LiveData<List<MyRecord>> = repository.records.asLiveData()

    fun insert(record: MyRecord) = viewModelScope.launch { repository.insert(record) }
    fun delete(record: MyRecord) = viewModelScope.launch { repository.delete(record) }

    private val count: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        count.value = 0
    }

    fun getCount(): LiveData<Int> { return count }
    // Main Thread 가 아닐 경우 count.postValue
    fun addCount() { count.value = count.value?.plus(1) }
}