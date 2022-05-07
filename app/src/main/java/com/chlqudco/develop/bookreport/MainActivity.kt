package com.chlqudco.develop.bookreport

import android.content.Intent
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
    private val db by lazy {  Room.databaseBuilder(this, AppDatabase::class.java, "bookDB").build()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //리사이클러 뷰 연결
        binding.MainRecyclerView.adapter = adapter
        binding.MainRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.MainAddBookButton.setOnClickListener {
            val intent = Intent(this,AddRecordActivity::class.java)
            startActivity(intent)
        }

        /*
        Thread{
            adapter.listData = db.bookDao().getAll() as MutableList<BookEntity>
            adapter.notifyDataSetChanged()
        }.start()


         */




    }

    override fun onResume() {
        super.onResume()

        Thread(Runnable {
            db.bookDao().getAll().run {
                runOnUiThread {
                    if (this.isNotEmpty()) {
                        binding.MainCenterTextView.isVisible = false
                        adapter.listData = this as MutableList<BookEntity>
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }).start()
    }
}