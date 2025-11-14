rootProject.name = "api"

// App
include(":app")

// Common
include(":common")

// Iam
include(":iam:application")
include(":iam:domain")
include(":iam:infrastructure")
include(":iam:presentation")

// Content
include(":conversation:application")
include(":conversation:domain")
include(":conversation:infrastructure")
include(":conversation:presentation")

// Subscription
include(":subscription:application")
include(":subscription:domain")
include(":subscription:infrastructure")
include(":subscription:presentation")

// Payment
include(":payment:application")
include(":payment:domain")
include(":payment:infrastructure")
include(":payment:presentation")
