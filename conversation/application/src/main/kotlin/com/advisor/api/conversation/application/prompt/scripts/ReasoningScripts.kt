package com.advisor.api.conversation.application.prompt.scripts

import com.advisor.api.ai_prompt_core.model.PromptType

object ReasoningScripts {
    // [STRATEGIC] 카피라이팅 및 이미지 기획용
    const val STRATEGIC_ANALYSIS = """
        [STRATEGIC THINKING PROCESS]
        1. Target Audience: Analyze the psychological triggers and pain points of the demographic.
        2. Visual Synergy: Consider how the text will complement the image's focal points.
        3. Hook & Value: Identify the most "thumb-stopping" element of the offer.
    """

    // [TECHNOLOGICAL] 레이아웃 분석 및 좌표 계산용
    const val SPATIAL_COMPOSITION = """
        [SPATIAL COMPOSITION LOGIC]
        1. Aspect Ratio: The canvas is a 1:1 square (1080x1080).
        2. Negative Space: Identify empty areas where text won't obscure the main subject.
        3. UI Awareness: Reserve safe zones for Instagram UI (top 15%, bottom 20%).
        4. Coordinates: Calculate (x, y) as percentages (0-100) and widths relative to the canvas.
    """

    // [VALIDATION] 모든 작업의 마무리 검토용
    const val QUALITY_CHECK = """
        [FINAL QUALITY CHECK]
        - Check legibility and contrast against the background.
        - Verify the tone matches the requested speech style.
        - Ensure all constraints and forbidden words are respected.
    """

    // [CONTROL] JSON 출력 강제
    const val STRICT_JSON_FORMAT = """
        [STRICT OUTPUT RULE]
        - Return ONLY raw JSON. No code blocks, no preamble. 
        - Must start with '{' and end with '}'.
    """

    fun guidelines(promptType: PromptType): List<String> {
        return when (promptType) {
            PromptType.COPY_WRITING -> listOf(
                STRATEGIC_ANALYSIS,
                QUALITY_CHECK
            )

            PromptType.IMAGE_GENERATION -> listOf(
                STRATEGIC_ANALYSIS
            )

            PromptType.LAYOUT_ANALYSIS -> listOf(
                SPATIAL_COMPOSITION,
                STRICT_JSON_FORMAT
            )
        }
    }
}
