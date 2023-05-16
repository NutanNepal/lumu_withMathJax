package com.lumu.snail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lumu.snail.databinding.FragmentChaptersBinding

class MyChaptersRecyclerViewAdapter(
    category: Category,
    private val onItemClickListener: OnChapterItemClickListener
) : RecyclerView.Adapter<MyChaptersRecyclerViewAdapter.ViewHolder>() {

    // Store the chapter list as a property
    private val chapterList = category.chapterList

    // Create the ViewHolder by inflating the view from the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentChaptersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = chapterList[position]
        holder.idView.text = buildString {
            append((position + 1).toString())
            append('.')
        }
        holder.contentView.text = chapter
        holder.itemView.setOnClickListener {
            onItemClickListener.onChapterItemClick(chapter)
        }
    }

    // Return the number of items in the list
    override fun getItemCount(): Int = chapterList.size

    // The ViewHolder holds references to the views in the layout
    inner class ViewHolder(binding: FragmentChaptersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }

    // Interface for handling chapter item click events
    interface OnChapterItemClickListener {
        fun onChapterItemClick(chapter: String)
    }
}