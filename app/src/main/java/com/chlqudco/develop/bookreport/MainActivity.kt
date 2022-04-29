package com.chlqudco.develop.bookreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.chlqudco.develop.bookreport.Entity.BookEntity
import com.chlqudco.develop.bookreport.database.AppDatabase
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
        val db = Room.databaseBuilder(
            this, AppDatabase::class.java, "bookDB"
        ).build()

        Thread(Runnable {
            db.bookDao().getAll().run {
                runOnUiThread {
                    if(this.isNotEmpty()){
                        binding.MainCenterTextView.isVisible = false
                        adapter.listData = this as MutableList<BookEntity>
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}