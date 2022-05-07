package com.chlqudco.develop.bookreport

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.chlqudco.develop.bookreport.Entity.BookEntity
import com.chlqudco.develop.bookreport.database.AppDatabase
import com.chlqudco.develop.bookreport.databinding.ActivityAddRecordBinding
import com.chlqudco.develop.bookreport.databinding.ActivityMainBinding
import java.sql.Types.NULL
import java.util.*

class AddRecordActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddRecordBinding.inflate(layoutInflater) }
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        db = Room.databaseBuilder(this,AppDatabase::class.java,"bookDB").build()
    }

    private fun initViews() = with(binding) {
        //책 사진 찍기 버튼
        AddRecordAddImageButton.setOnClickListener {

        }

        //등록하기 버튼
        AddRecordRegisterButton.setOnClickListener {
            //예외처리 1. 제목 비었는지
            if (AddRecordTitleEditTextView.text.isEmpty()){
                Toast.makeText(this@AddRecordActivity,"제목을 입력해 주세요",Toast.LENGTH_SHORT).show()
            }
            //예외처리 2. 내용 비었는지
            if(AddRecordReportEditText.text.isEmpty()){
                Toast.makeText(this@AddRecordActivity,"내용을 입력해 주세요",Toast.LENGTH_SHORT).show()
            }

            //정보 빼오기
            val title = AddRecordTitleEditTextView.text.toString()
            val record = AddRecordReportEditText.text.toString()
            val rating = AddRecordRatingBar.rating
            var imageUri : Uri? = null

            //쓰레드 열어서 저장
            Thread{
                val bookRecord = BookEntity(null, title, record, rating, System.currentTimeMillis(), imageUri?.toString())
                db.bookDao().insertBookReport(bookRecord)
                runOnUiThread {
                    finish()
                }
            }.start()

        }
    }
}