package com.lumu.snail.flashcardsActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.lumu.snail.R
import com.lumu.snail.mathjax.MathJaxParse

class FlashcardFragment(
    private var question: String,
    private var answer: String) : Fragment() {
    constructor():this("","")

    private lateinit var questionCardView: CardView
    private lateinit var answerCardView: CardView
    private lateinit var questionWebView: WebView
    private lateinit var answerWebView: WebView
    private var isShowingQuestion = true
    private var animationDuration: Long = 150
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard, container, false)
        questionCardView = view.findViewById(R.id.question_cardView)
        answerCardView= view.findViewById((R.id.answer_cardView))
        questionCardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.launcher
        ))
        answerCardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.launcher
        ))
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionWebView = questionCardView.findViewById(R.id.question_webView)
        answerWebView= answerCardView.findViewById(R.id.answer_webView)

        if (savedInstanceState != null) {
            question = savedInstanceState.getString(QUESTION, "")
            answer = savedInstanceState.getString(ANSWER, "")
        }
        enableSettings(questionWebView)
        enableSettings(answerWebView)
        renderStrings(question, answer)

        val scale = requireContext().resources.displayMetrics.density
        val distance = 15000
        questionCardView.cameraDistance = distance * scale
        answerCardView.cameraDistance = distance * scale
    }

    fun flipflip(){
        if (isShowingQuestion) {
            flipCard()
        } else {
            flipCardBack()
        }
    }

    private fun flipCard() {
        ViewCompat.animate(questionCardView)
            .rotationY(-90f)
            .setDuration(animationDuration)
            .withEndAction {
                isShowingQuestion = false
                questionCardView.visibility = View.GONE
                answerCardView.visibility = View.VISIBLE
                // Set rotationY value here
                answerCardView.rotationY = 90f
                // Start second animation
                ViewCompat.animate(answerCardView)
                    .rotationY(0f)
                    .setDuration(animationDuration)
                    .start()
            }
            .start()
    }

    private fun flipCardBack() {
        ViewCompat.animate(answerCardView)
            .rotationY(90f)
            .setDuration(animationDuration)
            .withEndAction {
                isShowingQuestion = true
                answerCardView.visibility = View.GONE
                questionCardView.visibility = View.VISIBLE
                // Set rotationY value here
                questionCardView.rotationY = -90f
                // Start second animation
                ViewCompat.animate(questionCardView)
                    .rotationY(0f)
                    .setDuration(animationDuration)
                    .start()
            }
            .start()
    }
    private fun renderStrings(question: String, answer: String) {
        questionWebView.clearCache(true)
        answerWebView.clearCache(true)
        val questionHtmlContent = MathJaxParse.generateHtmlContent(question)
        questionWebView.loadDataWithBaseURL(null, questionHtmlContent, "text/html", "utf-8", null)

        val answerHtmlContent = MathJaxParse.generateHtmlContent(answer)
        answerWebView.loadDataWithBaseURL(null, answerHtmlContent, "text/html", "utf-8", null)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun enableSettings(view: WebView){
        view.settings.javaScriptEnabled=true
        view.settings.domStorageEnabled=true
        view.settings.allowFileAccess = true
        view.settings.allowContentAccess = true
        view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        view.settings.displayZoomControls = false
        view.settings.builtInZoomControls = false
        view.settings.setSupportZoom(false)
        view.settings.useWideViewPort = false
    }

    companion object {
        private const val QUESTION = "question"
        private const val ANSWER = "answer"
        fun newInstance(question: String, answer: String) = FlashcardFragment(question,answer)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUESTION, question)
        outState.putString(ANSWER, answer)
    }

    override fun onResume() {
        super.onResume()
        if(!isShowingQuestion){
            flipCardBack()
        }
    }
}