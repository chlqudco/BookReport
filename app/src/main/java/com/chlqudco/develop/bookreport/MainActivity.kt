package com.chlqudco.develop.bookreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chlqudco.develop.bookreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { BookAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //리사이클러 뷰 연결
        binding.MainRecyclerView.adapter = adapter
        binding.MainRecyclerView.layoutManager = LinearLayoutManager(this)

        //데이터 불러오기
    }
}