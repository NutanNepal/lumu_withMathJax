package com.lumu.snail.sage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lumu.snail.R

class SageFragment(
    private var question: String,
    private var answer: String) : Fragment() {
    constructor():this("","")

    private lateinit var questionCardView: CardView
    private lateinit var questionWebView: WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard, container, false)
        questionCardView = view.findViewById(R.id.question_cardView)
        questionCardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.launcher))
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionWebView = questionCardView.findViewById(R.id.question_webView)

        if (savedInstanceState != null) {
            question = savedInstanceState.getString(QUESTION, "")
            answer = savedInstanceState.getString(ANSWER, "")
        }
        enableSettings(questionWebView)
        renderStrings(question)
    }

    private fun renderStrings(question: String) {
        questionWebView.loadDataWithBaseURL("https://sagecell.sagemath.org/", question, "text/html", "utf-8", null)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun enableSettings(view: WebView){

        view.webViewClient = MyWebClient()
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
        fun newInstance(question: String, answer: String) = SageFragment(question,answer)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUESTION, question)
        outState.putString(ANSWER, answer)
    }
}