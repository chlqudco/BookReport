package com.chlqudco.develop.bookreport.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chlqudco.develop.bookreport.Entity.BookEntity

@Dao
interface BookDao {

    //저장된 모든 독후감 가져오기
    @Query("SELECT * FROM book")
    fun getAll(): List<BookEntity>

    //독후감 저장하기
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertBookReport(book: BookEntity)

    //독후감 삭제
    @Query("DELETE FROM book WHERE id = :id")
    fun delete(id : Int)
}