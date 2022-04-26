package com.example.myrecoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecoder.databinding.ActivityHistoryBinding
import com.example.myrecoder.databinding.ActivityMainBinding
import com.example.myrecoder.model.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }
    private val adapter = HistoryAdapter()
    private val viewModel by lazy { MainViewModel((application as MyRecordApplication).repository) }

    private val callBack = object :ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewModel: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.delete(adapter.getItem(viewHolder.layoutPosition))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        ItemTouchHelper(callBack).attachToRecyclerView(binding.recyclerView)

        viewModel.records.observe(this) {
            adapter.updateData(it)
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            val dao = MyDatabase.getInstance(this@HistoryActivity)?.myDao()
//            val result = dao?.selectAll()!!
//            withContext(Dispatchers.Main) {
//                adapter.updateData(result)
//            }
//        }
    }

}