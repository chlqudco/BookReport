package com.chlqudco.develop.bookreport

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.chlqudco.develop.bookreport.Entity.BookEntity
import com.chlqudco.develop.bookreport.database.AppDatabase
import com.chlqudco.develop.bookreport.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var db : AppDatabase
    private var imageUri : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "bookDB").build()

        val bookModel = intent.getParcelableExtra<BookEntity>("bookModel")

        //입력된 값 복원
        binding.DetailTitleEditTextView.setText(bookModel?.title)
        binding.DetailRatingBar.rating = bookModel?.starRate!!
        binding.DetailReportEditText.setText(bookModel.report)
        bookModel.imageUri?.let {
            binding.DetailImageView.setImageBitmap(BitmapConverter().stringToBitmap(bookModel.imageUri))
            imageUri = bookModel.imageUri
        }

        //사진 등록 버튼
        binding.DetailAddImageButton.setOnClickListener {
            //권한 처리
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(this, AddRecordActivity.REQUIRED_PERMISSIONS, AddRecordActivity.REQUEST_CODE_PERMISSIONS)
            }
        }

        //수정 완료 버튼
        binding.DetailRegisterButton.setOnClickListener {
            //수정된 값 불러오기
            val title = binding.DetailTitleEditTextView.text.toString()
            val report = binding.DetailReportEditText.text.toString()
            val starRate = binding.DetailRatingBar.rating

            //재 삽입
            Thread{
                val record = BookEntity(bookModel.id, title, report, starRate, bookModel.date, imageUri)
                db.bookDao().insertBookReport(record)
                runOnUiThread{
                    finish()
                }
            }.start()
        }

    }

    private fun allPermissionsGranted() = AddRecordActivity.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AddRecordActivity.REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera()
            }else{
                Toast.makeText(this,"동의해야 카메라를 실행할 수 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 102 && resultCode == Activity.RESULT_OK){
            data?.extras?.get("data")?.let {
                val bitmap = it as Bitmap
                imageUri = BitmapConverter().bitmapToString(bitmap)
                binding.DetailImageView.setImageBitmap(bitmap)
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 102)
    }
}