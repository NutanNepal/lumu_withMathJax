package com.lumu.snail

class MathJaxParse{
    companion object{
        // set the MathJax configuration options
        private const val mathJaxUrl = "file:///android_asset/mathjax/es5/tex-chtml.js"
        private const val mathJaxConfig = """
        MathJax.Hub.Config({
        extensions: ["tex2jax.js"],
        jax: ["input/TeX", "output/CommonHTML"],
        tex2jax: {
            inlineMath: [['\\(', '\\)'], ['$', '$']],
            displayMath: [['$$', '$$'], ['\\[', '\\]']],
            processEscapes: true
        },
        showMathMenu: false,
        messageStyle: "none"
        });
        """
        fun generateHtmlContent(inputString: String): String {
            val htmlContent = """
                <html>
                <head>
                    <style>
                        @font-face {
                            font-family: "CompModern";
                            src: url('file:///android_res/font/cmunrm.ttf');
                        }
                        body {
                            background-color:#121225;
                            color:#ffffff;
                            font-family: "CompModern", sans-serif;
                            font-size: 20px;
                            line-height: 1.5;
                        }
                    </style>
                    <script src='$mathJaxUrl' id="MathJax-script" async></script>
                    <script type='text/x-mathjax-config'>$mathJaxConfig</script>
                </head>
                <body>$inputString</body>
                </html>
            """
            return htmlContent.trimIndent()
        }
    }
}