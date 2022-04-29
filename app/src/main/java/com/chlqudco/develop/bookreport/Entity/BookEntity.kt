package com.chlqudco.develop.bookreport.Entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
class BookEntity {
    constructor(title: String, report: String, starRate: Int, date: Long, uri: Uri) {
        this.title = title
        this.report = report
        this.starRate = starRate
        this.date = date
        this.imageUri = uri
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo
    var title: String = ""
    @ColumnInfo
    var report: String = ""
    @ColumnInfo
    var starRate: Int = 0
    @ColumnInfo
    var date: Long = 0
    @ColumnInfo
    var imageUri: Uri

}