package com.lumu.snail

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.lumu.snail.categoriesfragment.CategoriesFragment
import com.lumu.snail.categoriesfragment.MyCategoriesRecyclerViewAdapter
import com.lumu.snail.chaptersfragment.ChaptersFragment
import com.lumu.snail.chaptersfragment.MyChaptersRecyclerViewAdapter
import com.lumu.snail.flashcardsActivity.FlashcardActivity
import com.lumu.snail.tableOfContents.Category


class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener {

    private var currentTitle = "Home"

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            putString("CURRENT_NAV_STATE", currentTitle)
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Hide the system window decorations
        WindowCompat.setDecorFitsSystemWindows(window, false)
        supportActionBar!!.hide()
        val btnBack = this.findViewById<ImageButton>(R.id.btn_back)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    if (currentTitle == "Home") {
                        // If already on the CategoriesFragment, call the default back behavior (exit the app)
                        finish()
                    } else {
                        // Otherwise, go back to the CategoriesFragment and update the title
                        currentTitle = "Home"
                        replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
                        btnBack.animate().rotationXBy(-90f)
                            .setDuration(400)
                            .setInterpolator(LinearInterpolator())
                            .withEndAction {
                                btnBack.visibility = View.INVISIBLE
                            }
                            .start()
                    }
                }
            }
        // Add the callback to the activity's back stack
        onBackPressedDispatcher.addCallback(this, callback)

        if (savedInstanceState != null) {
            currentTitle =
                savedInstanceState.getString("CURRENT_NAV_STATE", currentTitle) ?: "Home"
        }

        if (currentTitle == "Home") {
            replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
        } else {
            onCategoryItemClick(Category.valueOf(currentTitle))
        }
    }

    override fun onCategoryItemClick(category: Category) {
        // Update the title with the selected category name
        currentTitle = category.toString()
        val btnBack = this@MainActivity.findViewById<ImageButton>(R.id.btn_back)
        this@MainActivity.findViewById<TextView>(R.id.textView).text = currentTitle

        btnBack.animate()
            .withStartAction {
                btnBack.rotationX = 90F
                btnBack.visibility = View.VISIBLE
            }
            .withEndAction{
            btnBack.animate()
                .rotationXBy(90F)
                .setDuration(200)
                .setInterpolator(LinearInterpolator())
                .start()
            }
            .start()
        // Replace the current fragment with a new ChaptersFragment for the selected category
        val fragment = ChaptersFragment.newInstance(category)
        replaceFragmentContainer(R.id.fragmentContainerView, fragment)
    }

    override fun onChapterItemClick(chapter: String) {
        // Start the FlashcardActivity for the selected chapter
        startActivity(
            FlashcardActivity.newIntent(this@MainActivity, chapter),
            ActivityOptions.makeSceneTransitionAnimation(
                this@MainActivity).toBundle()
        )
    }

    fun replaceFragmentContainer(oldFragment: Int, newFragment: Fragment) {
        this.findViewById<TextView>(R.id.textView).text = currentTitle
        // Get the FragmentManager
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the default fragment container with the list fragment
        fragmentTransaction.replace(oldFragment, newFragment)

        // Add the current fragment to the back stack
        fragmentTransaction.addToBackStack(null)

        // Commit the transaction
        fragmentTransaction.commit()
    }
}