rootProject.name = "api"

// App
include(":app")

// Common
include(":common")

// Iam
include(":iam:application")
include(":iam:domain")
include(":iam:port")
include(":iam:adapter:inbound")
include(":iam:adapter:outbound")

// Conversation
include(":conversation:application")
include(":conversation:domain")
include(":conversation:port:inbound")
include(":conversation:port:outbound")
include(":conversation:adapter:inbound")
include(":conversation:adapter:outbound")

// Subscription
include(":subscription:application")
include(":subscription:domain")
include(":subscription:port")
include(":subscription:adapter:inbound")
include(":subscription:adapter:outbound")

// Payment
include(":payment:application")
include(":payment:domain")
include(":payment:port")
include(":payment:adapter:inbound")
include(":payment:adapter:outbound")

// Token Usage
include(":token_usage:application")
include(":token_usage:domain")
include(":token_usage:port")
include(":token_usage:adapter:inbound")
include(":token_usage:adapter:outbound")

// Media
include(":media:application")
include(":media:domain")
include(":media:port")
include(":media:adapter:inbound")
include(":media:adapter:outbound")

// AI Prompt Core
include(":ai_prompt_core")
