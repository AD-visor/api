rootProject.name = "api"

// App
include(":app")

// Common
include(":common")

// Iam
include(":iam:application")
include(":iam:domain")
include(":iam:port:inbound")
include(":iam:port:outbound")
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
include(":subscription:port:inbound")
include(":subscription:port:outbound")
include(":subscription:adapter:inbound")
include(":subscription:adapter:outbound")

// Payment
include(":payment:application")
include(":payment:domain")
include(":payment:port:inbound")
include(":payment:port:outbound")
include(":payment:adapter:inbound")
include(":payment:adapter:outbound")
