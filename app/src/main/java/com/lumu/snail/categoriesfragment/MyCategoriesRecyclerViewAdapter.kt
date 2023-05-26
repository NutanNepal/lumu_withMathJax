// This class is a RecyclerView Adapter for displaying a list of Categories.
// It receives a list of Categories and an OnCategoryItemClickListener as input.
// The OnCategoryItemClickListener is used to handle clicks on individual categories in the list.

package com.lumu.snail.categoriesfragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lumu.snail.databinding.FragmentCategoryBinding
import com.lumu.snail.tableOfContents.Category

class MyCategoriesRecyclerViewAdapter(
    private val values: List<Category>,
    private val onItemClickListener: OnCategoryItemClickListener
) : RecyclerView.Adapter<MyCategoriesRecyclerViewAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = values[position]
        holder.idView.text = buildString {
            append(position + 1)
            append(".")
        }
        holder.contentView.text = category.toString()
        holder.itemView.setOnClickListener {
            onItemClickListener.onCategoryItemClick(category)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }

    // Interface to handle click events on the categories
    interface OnCategoryItemClickListener {
        fun onCategoryItemClick(category: Category)
    }
}
