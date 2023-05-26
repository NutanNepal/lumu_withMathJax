package com.lumu.snail.sage

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


open class MyWebClient : WebViewClient() {
    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)

        view.setInitialScale(200)

        /*
        // Retrieve the contents of the `sagecell_sessionOutput` div class
        view.evaluateJavascript(
            """
            javascript:(function() {
                var observer = new MutationObserver(function(mutations) {
                    mutations.forEach(function(mutation) {
                        if (mutation.target.className === 'sagecell_sessionOutput') {
                            Android.onContentReady(mutation.target.innerHTML);
                        }
                    });
                });
                
                var targetNode = document.getElementsByClassName('sagecell_sessionOutput')[0];
                observer.observe(targetNode, { childList: true });
            })();
            """.trimIndent(),
            null
        )
         */
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        // Handle WebView errors here
    }
}