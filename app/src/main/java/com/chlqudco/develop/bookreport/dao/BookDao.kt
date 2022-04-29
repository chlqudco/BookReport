package com.chlqudco.develop.bookreport.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chlqudco.develop.bookreport.Entity.BookEntity

@Dao
interface BookDao {

    //저장된 모든 독후감 가져오기
    @Query("SELECT * FROM book")
    fun getAll(): List<BookEntity>

    //새로운 독후감 저장하기
    @Insert
    fun insertBookReport(book: BookEntity)
}