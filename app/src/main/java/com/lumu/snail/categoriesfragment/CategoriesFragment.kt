package com.lumu.snail.categoriesfragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lumu.snail.R
import com.lumu.snail.tableOfContents.Category


class CategoriesFragment : Fragment(){
    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, container, false)
        val categories :List<Category> = enumValues<Category>().toList()
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyCategoriesRecyclerViewAdapter(categories,
                    activity as MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener
                )
            }
        }
        return view
    }
}