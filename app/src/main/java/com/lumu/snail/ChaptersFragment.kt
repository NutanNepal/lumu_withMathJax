package com.lumu.snail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChaptersFragment(private val category: Category): Fragment() {
    constructor() : this(Category.Subjects)
        // initialize any fields you need to her
    private var columnCount = 1
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            putString("CURRENT_NAV_STATE", category.toString())
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    // onCreateView() is called when the fragment should create its UI.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment_chapters_list layout.
        val view = inflater.inflate(R.layout.fragment_chapters_list, container, false)
        // If the inflated view is a RecyclerView, set up the layout manager and adapter.
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    // If there's only one column, use a vertical LinearLayoutManager.
                    columnCount <= 1 -> LinearLayoutManager(context)
                    // Otherwise, use a GridLayoutManager with the specified number of columns.
                    else -> GridLayoutManager(context, columnCount)
                }
                // Set the adapter for the RecyclerView.
                adapter = MyChaptersRecyclerViewAdapter(
                    category,
                    // The activity hosting this fragment must implement the OnChapterItemClickListener interface.
                    activity as MyChaptersRecyclerViewAdapter.OnChapterItemClickListener
                )
            }
        }

        // Return the inflated view.
        return view
    }

    // This companion object contains constant values and a factory method for creating instances of this fragment.
    companion object {

        // Factory method for creating instances of this fragment.
        @JvmStatic
        fun newInstance(category: Category) =
            ChaptersFragment(category)
            }
}