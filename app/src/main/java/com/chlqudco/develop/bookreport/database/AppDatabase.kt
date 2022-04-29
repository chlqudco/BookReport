package com.chlqudco.develop.bookreport.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chlqudco.develop.bookreport.Entity.BookEntity
import com.chlqudco.develop.bookreport.dao.BookDao

@Database(entities = arrayOf(BookEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}