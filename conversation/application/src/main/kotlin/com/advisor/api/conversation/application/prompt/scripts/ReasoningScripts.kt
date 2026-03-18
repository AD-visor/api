package com.advisor.api.conversation.application.prompt.scripts

import com.advisor.api.ai_prompt_core.model.PromptType

object ReasoningScripts {
    const val COPY_WRITING_REASONING = """
        [COPYWRITING PROCESS]
        1. Understand the core message and emotion
        2. Identify the single most powerful benefit
        3. Choose words that are:
           - Short (5-15 Korean characters)
           - Visually beautiful in Korean typography
           - Emotionally resonant
           - Instantly memorable
        4. Test: Can someone remember this after seeing it for 1 second?
    """

    const val INTEGRATED_DESIGN_THINKING = """
        [COMPLETE DESIGN THINKING PROCESS]
        1. UNDERSTAND THE MESSAGE
           - What is the core value proposition?
           - What emotion should this evoke?
           - Who is the target audience?
        
        2. CHOOSE DESIGN DIRECTION
           - Which style best communicates the message? (Minimalist/Bold/Warm/Premium/Energetic)
           - What typography style suits the Korean text and brand?
           - What color palette reinforces the message?
        
        3. COMPOSE THE DESIGN
           - How should the Korean text be positioned for maximum impact?
           - What size and weight makes it readable yet striking?
           - How do visual elements support (not compete with) the text?
        
        4. ENSURE COHESION
           - Do text and visuals work together as one piece?
           - Is there a clear visual hierarchy?
           - Would this stop someone's scroll on Instagram?
        
        5. TECHNICAL VALIDATION
           - Is text away from Instagram UI zones?
           - Is contrast sufficient for mobile viewing?
           - Is the design 1024x1024px and Instagram-optimized?
    """

    const val TYPOGRAPHY_EXCELLENCE = """
        [KOREAN TYPOGRAPHY PRINCIPLES]
        - SCALE: Make Korean text large enough to be impactful (headline should dominate)
        - WEIGHT: Use appropriate font weights (bold for headlines, medium for subtext)
        - SPACING: Korean characters need proper spacing for readability
        - CONTRAST: Ensure high contrast between text and background
        - HIERARCHY: Clear distinction between headline, subtext, and CTA
        - STYLE: Choose font style that matches brand personality
    """

    const val STRATEGIC_ANALYSIS = """
        [STRATEGIC THINKING PROCESS]
        1. Target Audience: Analyze psychological triggers and pain points
        2. Message Clarity: Ensure the copy is instantly understandable
        3. Visual Impact: Design should stop the scroll in 0.5 seconds
        4. Brand Alignment: Style must match brand personality
    """

    const val DESIGN_CONCEPT_REASONING = """
        [DESIGN CONCEPT DEVELOPMENT]
        1. Analyze the marketing objective and user request
        2. Identify the target audience's preferences and psychology
        3. Determine the most effective design style
        4. Specify Korean typography treatment in detail
        5. Provide concrete visual direction (colors, composition, mood)
    """

    fun guidelines(promptType: PromptType): List<String> {
        return when (promptType) {
            PromptType.COPY_WRITING -> listOf(
                COPY_WRITING_REASONING,
                STRATEGIC_ANALYSIS
            )
            PromptType.INTEGRATED_DESIGN -> listOf(
                INTEGRATED_DESIGN_THINKING,
                TYPOGRAPHY_EXCELLENCE,
                STRATEGIC_ANALYSIS
            )
            PromptType.DESIGN_CONCEPT -> listOf(
                DESIGN_CONCEPT_REASONING,
                STRATEGIC_ANALYSIS
            )
        }
    }
}
