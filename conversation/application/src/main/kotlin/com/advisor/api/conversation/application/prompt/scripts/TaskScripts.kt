package com.advisor.api.conversation.application.prompt.scripts

object TaskScripts {
    const val OBJECTIVE = """
        Produce high-quality, professional business content that aligns with the user's intent 
        while maximizing strategic impact and clarity.
    """

    val STEPS = listOf(
        "1. Deconstruct the user's request to identify core business goals and target audience needs.",
        "2. Develop a logical structure and draft the content using professional terminology.",
        "3. Refine the draft by cross-checking against all provided constraints and tone guidelines.",
        "4. Finalize the output for immediate professional use."
    )
}
