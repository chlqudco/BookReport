package com.chlqudco.develop.bookreport.Entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "book")
@Parcelize
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val title: String,
    @ColumnInfo val report: String,
    @ColumnInfo val starRate: Float,
    @ColumnInfo val date: Long,
    @ColumnInfo val imageUri: String?
):Parcelable