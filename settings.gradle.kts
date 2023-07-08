@file:Suppress("UnstableApiUsage")

include(":scope")

dependencyResolutionManagement {
    versionCatalogs { create("libs") { from(files("libs.versions.toml")) } }
}
