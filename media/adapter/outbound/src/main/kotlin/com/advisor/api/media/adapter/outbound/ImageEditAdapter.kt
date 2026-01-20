package com.advisor.api.media.adapter.outbound

import com.advisor.api.media.port.outbound.ImageEditPort
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import com.advisor.api.media.port.outbound.result.ImageEditResult
import com.microsoft.playwright.Browser
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.options.LoadState
import com.microsoft.playwright.options.ScreenshotType
import org.springframework.stereotype.Component
import kotlin.io.encoding.Base64

@Component
class ImageEditAdapter(
    private val playwright: Playwright,
    private val browser: Browser
): ImageEditPort {
    override fun composite(command: ImageEditCommand.Composite): ImageEditResult {
        val base64Image = Base64.encode(command.baseImage)

        // 1. HTML/CSS 조립
        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css">
                <link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
                <link href="https://fonts.cdnfonts.com/css/gmarket-sans" rel="stylesheet">
                <style>
                    body {
                        margin: 0; padding: 0;
                        width: ${command.canvasWidth}px; height: ${command.canvasHeight}px;
                        background-image: url('data:image/png;base64,$base64Image');
                        background-size: cover;
                        position: relative;
                        overflow: hidden;
                        font-family: 'Pretendard', sans-serif; /* 필요 시 웹폰트 추가 */
                        -webkit-font-smoothing: antialiased;
                    }
                    .text-element {
                        position: absolute;
                        display: flex;
                        flex-direction: column;
                        word-break: keep-all;
                    }
                </style>
            </head>
            <body>
                ${command.textElements.joinToString("") { renderElement(it) }}
            </body>
            </html>
        """.trimIndent()

        // Page만 생성해서 사용
        val context = browser.newContext(Browser.NewContextOptions().setViewportSize(command.canvasWidth, command.canvasHeight))
        val page = context.newPage()
        try {
            page.setContent(htmlContent)
            page.waitForLoadState(LoadState.NETWORKIDLE)

            val screenshot = page.screenshot(Page.ScreenshotOptions().apply {
                setType(ScreenshotType.PNG)
                setFullPage(false)
            })
            return ImageEditResult(screenshot)
        } finally {
            page.close()
            context.close()
        }
    }

    private fun renderElement(element: ImageEditCommand.TextElement): String {
        val s = element.style

        val cssFont = when(s.fontFamily?.lowercase()) {
            "bold" -> "'Black Han Sans', sans-serif"
            "serif" -> "'Nanum Myeongjo', serif"
            "modern" -> "'Gmarket Sans', sans-serif"
            else -> "'Pretendard', sans-serif"
        }

        // CSS 스타일 속성으로 변환
        val inlineStyle = listOfNotNull(
            s.top?.let { "top: $it" },
            s.left?.let { "left: $it" },
            "width: ${s.width}",
            "font-family: $cssFont",
            "font-size: ${s.fontSize}",
            "color: ${s.fontColor}",
            "font-weight: ${s.fontWeight}",
            "text-align: ${s.textAlign}",
            s.backgroundColor?.let { "background-color: $it" },
            s.textShadow?.let { "text-shadow: $it" },
            "padding: ${s.padding}",
            "border-radius: ${s.borderRadius}"
        ).joinToString("; ")

        return "<div class='text-element' style='$inlineStyle'>${element.text}</div>"
    }
}
