package com.chlqudco.develop.bookreport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chlqudco.develop.bookreport.Entity.BookEntity
import com.chlqudco.develop.bookreport.databinding.ItemBookBinding
import java.text.SimpleDateFormat
import java.util.*

class BookAdapter: RecyclerView.Adapter<BookAdapter.Holder>() {
    var listData = mutableListOf<BookEntity>()

    inner class Holder(val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bookModel : BookEntity){
            binding.ItemTitleTextView.text = bookModel.title
            binding.ItemRatingBar.rating = bookModel.starRate

            val date = Date(bookModel.date)
            val sdf = SimpleDateFormat("yyyy/ MM/ dd")
            binding.ItemDateTextView.text = sdf.format(date)
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}