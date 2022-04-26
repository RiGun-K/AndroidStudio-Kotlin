package com.example.myrecoder

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrecoder.databinding.ActivityMainBinding
import com.example.myrecoder.model.MyDatabase
import com.example.myrecoder.model.MyRecord
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val sharedPreference by lazy {
        getSharedPreferences("recode", Context.MODE_PRIVATE)
    }

    val model by lazy { MainViewModel((application as MyRecordApplication).repository) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // MainViewModel.Class 불러쓰기
//        val model = MainViewModel()
        model.getCount().observe(this) { it ->
            binding.textViewCount.text = it.toString()
        }
        
        val lastFood = sharedPreference.getString("food", null)
        val lastTime = sharedPreference.getString("time", null)
        if(lastFood!= null && lastTime!= null) {
            displayRecord(lastFood, lastTime)
        }

        // layout 내의 버튼 id에 대해서 이벤트 처리
        binding.buttonRecord.setOnClickListener { onSave() }
        binding.buttonAddCount.setOnClickListener {
            model.addCount()
        }

        binding.buttonHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

    }

    // 클릭 이벤트 처리
    private fun onSave() {
        with(sharedPreference.edit()) {
            val food = binding.editTextFoodName.text.toString()
            if(food.isNotEmpty()) {
                this.putString("food", food)
                val time = LocalDateTime.now().toString()
                this.putString("time", time)
                this.apply()

                model.insert(MyRecord(O, food, time))

//                CoroutineScope(Dispatchers.IO).launch {
//                    val db = MyDatabase.getInstance(this@MainActivity)
//                    db.myDao().insert(MyRecord(0, food, time))
//                }
            }
        }
    }

    // DB INSERT 처리
    private fun displayRecord(lastFood: String, lastTime: String) {
        val lastTime = LocalDateTime.parse(lastTime)
        val now = LocalDateTime.now()
        // 상단 정보 출력
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm", Locale.KOREA)
        binding.textViewRecord.text = "${lastTime.format(formatter)} - $lastFood"
        // 하단 경과 시간 출력
        val hours = ChronoUnit.HOURS.between(lastTime, now)
        var minutes = ChronoUnit.MINUTES.between(lastTime, now)
        minutes -= 60*hours
        binding.textViewElapsed.text = String.format(Locale.KOREA, "%02d 시간 %02d분 경과"
            , hours, minutes)
    }
}