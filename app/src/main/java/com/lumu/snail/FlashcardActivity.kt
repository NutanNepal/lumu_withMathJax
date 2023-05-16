package com.lumu.snail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Xml
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.xmlpull.v1.XmlPullParser
import kotlin.random.Random

class FlashcardActivity: FragmentActivity() {
    // Views
    private lateinit var viewPager: ViewPager2

    //Saved flashcard instance state
    private var currentPosition: Int = 0
    private var currentSeed: Long = seed()

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the current position of the ViewPager
        outState.run{
            putInt(CURRENT_POSITION_KEY, currentPosition)
            putLong(CURRENT_SEED, currentSeed)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore the saved instance state if it exists
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION_KEY)
            currentSeed = savedInstanceState.getLong(CURRENT_SEED)
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set the content view for this activity
        setContentView(R.layout.activity_flashcard)

        // Set the chapter title from the extra provided by the intent
        this.findViewById<TextView>(R.id.flashcard_title).text = getChapter()

        // Get the ViewPager element from the layout
        viewPager = findViewById(R.id.viewPager)

        // Set the adapter for the ViewPager
        viewPager.adapter = FlashcardPagerAdapter(supportFragmentManager)
        viewPager.setCurrentItem(currentPosition, false)

        // Set the page transformer to the zoom out transformer
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        this.findViewById<FloatingActionButton>(R.id.flip_fab).setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag("f${viewPager.currentItem}")
            if (fragment is FlashcardFragment) {
                fragment.flipflip()
            }
        }

        // Add an OnPageChangeCallback to the ViewPager to update the current position
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
    }

    inner class FlashcardPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val chapter = getChapter()
        // Flashcard list
        val flashcards = parseFlashcards(chapter).shuffled(Random(currentSeed))

        // Return the number of flashcards
        override fun getItemCount(): Int = Int.MAX_VALUE

        // Create a new instance of the FlashcardFragment for each page
        override fun createFragment(position: Int): Fragment {
            val flashcard = flashcards[position % flashcards.size]
            return FlashcardFragment.newInstance(flashcard.question, flashcard.answer)
        }
    }

    // Parse the flashcards from the XML file
    private fun parseFlashcards(chapterList: String): MutableList<Flashcard> {
        val chapters : List<String> =
            if(Subjects.isSubjectName(chapterList.replace("\\s".toRegex(), ""))){
            Subjects.valueOf(chapterList.replace("\\s".toRegex(), "")).topics
            }
            else{
            listOf(chapterList)
            }

        val flashcards = mutableListOf<Flashcard>()
        for (filename in chapters){
            val fileName = "$filename.xml"
            val inputStream = fileName.let { assets.open(it) }

            // Create a new instance of the XmlPullParser interface
            val parser: XmlPullParser = Xml.newPullParser()

            // Set the input stream for the parser to the opened file
            parser.setInput(inputStream, null)
            flashcards.addAll(parseXml(parser))
        }
        return flashcards
    }

    // Parse the XML file and return a list of flashcards
    private fun parseXml(parser: XmlPullParser): MutableList<Flashcard> {
        val flashcards = mutableListOf<Flashcard>()
        var eventType = parser.eventType
        var currentFlashcard: Flashcard? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "flashcard" -> {
                            // Create a new Flashcard object when a <flashcard> tag is encountered
                            currentFlashcard = Flashcard("", "")
                        }

                        "question" -> {
                            // Set the question for the current Flashcard object when a <question> tag is encountered
                            currentFlashcard?.question = parser.nextText()
                        }

                        "answer" -> {
                            // Set the answer for the current Flashcard object when an <answer> tag is encountered
                            currentFlashcard?.answer = parser.nextText()
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (parser.name == "flashcard") {
                        // Add the current Flashcard object to the list when a </flashcard> tag is encountered
                        currentFlashcard?.let { flashcards.add(it) }
                        currentFlashcard = null
                    }
                }
            }
            eventType = parser.next()
        }
        return flashcards
    }

    private fun seed(): Long{
        return SystemClock.currentThreadTimeMillis()
    }

    private fun getChapter(): String{
        return intent?.getStringExtra(CHAPTER).toString()
    }

    companion object {
        private const val CHAPTER = "chapter"
        var CURRENT_POSITION_KEY = "current_position"
        var CURRENT_SEED = "current_seed"

        /**
         * Creates a new Intent for the FlashcardActivity
         *
         * @param context The context of the calling activity
         * @param chapterlist The name of the chapter to load
         * @return The Intent for starting the FlashcardActivity
         */
        fun newIntent(context: Context, chapterlist: String): Intent {
            return Intent(context, FlashcardActivity::class.java).apply {
                putExtra(CHAPTER, chapterlist)
            }
        }
    }
}

/**
* Represents a flashcard with a question and answer.
* @property question The question on the flashcard
* @property answer The answer on the flashcard
 */
class Flashcard(
    var question: String,
    var answer: String
)
