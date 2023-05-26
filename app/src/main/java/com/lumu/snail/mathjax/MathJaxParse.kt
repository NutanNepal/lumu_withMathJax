package com.lumu.snail.mathjax

class MathJaxParse{
    companion object{
        // set the MathJax configuration options
        private const val mathJaxUrl = "file:///android_asset/mathjax/es5/tex-chtml-full.js"
        private const val mathJaxConfig = """
        MathJax.Hub.Config({
            jax: ["input/TeX", "output/CommonHTML"],
            tex: {
                inlineMath: [['\\(', '\\)'], ['$', '$']],
                displayMath: [['$$', '$$'], ['\\[', '\\]']],
                processEscapes: true,
                processEnvironments: true,
                processRefs: true,
                digits: /^(?:[0-9]+(?:\{,\}[0-9]{3})*(?:\.[0-9]*)?|\.[0-9]+)/,
                tagSide: 'right',
                tagIndent: '0.8em',
                maxMacros: 1000,
                maxBuffer: 5 * 1024,
                formatError: (jax, err) => jax.formatError(err)
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
                            background-color:#181225;
                            color:#ffffff;
                            font-family: "CompModern", sans-serif;
                            font-size: 18px;
                            line-height: 1.3;
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