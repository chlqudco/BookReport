package com.chlqudco.develop.bookreport.Entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val title: String,
    @ColumnInfo val report: String,
    @ColumnInfo val starRate: Float,
    @ColumnInfo val date: Long,
    @ColumnInfo val imageUri: String?
)