package com.advisor.api.media.port.outbound.command

class ImageEditCommand {
    class Composite(
        val baseImage: ByteArray,
        val textElements: List<TextElement>,
        val canvasWidth: Int = 1024,
        val canvasHeight: Int = 1024
    )

    data class TextElement(
        val text: String,
        val style: TextStyle // LayoutSpec 대신 더 포괄적인 Style로 변경
    )

    data class TextStyle(
        // 위치 제어: 좌표 방식도 유지하되, 정렬 방식을 추가
        val top: String? = null,
        val left: String? = null,
        val width: String? = "100%",

        // 텍스트 스타일
        val fontSize: String,
        val fontColor: String,
        val fontWeight: String = "bold",
        val fontFamily: String? = null,
        val textAlign: String = "center",

        // HTML 방식의 핵심 장점: 효과 추가
        val backgroundColor: String? = null,
        val textShadow: String? = null,
        val padding: String? = "0px",
        val borderRadius: String? = "0px"
    )
}
