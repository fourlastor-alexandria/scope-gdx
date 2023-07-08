@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    versionCatalogs { create("libs") { from(files("libs.versions.toml")) } }
}

include(
    ":scope",
    ":example",
)
