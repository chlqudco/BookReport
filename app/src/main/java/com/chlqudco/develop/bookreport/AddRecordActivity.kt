package com.chlqudco.develop.bookreport

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private var imageUri : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        db = Room.databaseBuilder(this,AppDatabase::class.java,"bookDB").build()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera()
            }else{
                Toast.makeText(this,"동의해야 카메라를 실행할 수 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            data?.extras?.get("data")?.let {
                val bitmap = it as Bitmap
                imageUri = BitmapConverter().bitmapToString(bitmap)
                binding.AddRecordImageView.setImageBitmap(bitmap)
            }
        }
    }

    private fun initViews() = with(binding) {

        //책 사진 찍기 버튼
        AddRecordAddImageButton.setOnClickListener {
            //권한 처리
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(this@AddRecordActivity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            }
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

            //쓰레드 열어서 저장
            Thread{
                val bookRecord = BookEntity(null, title, record, rating, System.currentTimeMillis(), imageUri)
                db.bookDao().insertBookReport(bookRecord)
                runOnUiThread {
                    finish()
                }
            }.start()

        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 101)
    }

    companion object{
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}