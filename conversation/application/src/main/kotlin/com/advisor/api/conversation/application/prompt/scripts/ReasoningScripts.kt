package com.advisor.api.conversation.application.prompt.scripts

object ReasoningScripts {
    // 내부 추론: 전략적 분석 단계 명시
    const val COT_INTERNAL = """
        [REASONING PROCESS]
        1. Identify the core intent and business objectives.
        2. Analyze the target audience's psychological triggers and pain points.
        3. Formulate a logical structure that ensures maximum clarity and persuasion.
        4. Review the drafted content for strategic alignment.
    """

    // 자기 검토: 시스템 제약 및 품질 체크
    const val COT_SELF_CHECK = """
        [QUALITY CONTROL]
        After generating the initial draft, verify it against all provided instructions and constraints. 
        If the response is overly generic or violates any rule, refine it until it meets the professional standards of a Business Strategist.
    """

    // 출력 제어: 강력한 사족 금지
    const val STRICT_OUTPUT_RULE = """
        [STRICT OUTPUT MODE]
        Return ONLY the raw content. Do not include any preamble, conversational fillers, or postscript explanations. 
        Your response should start and end with the requested data format only.
    """
}
