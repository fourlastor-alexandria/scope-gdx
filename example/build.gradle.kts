@Suppress(
    // known false positive: https://youtrack.jetbrains.com/issue/KTIJ-19369
    "DSL_SCOPE_VIOLATION"
)
plugins {
    java
    application
    alias(libs.plugins.spotless)
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":scope"))
    implementation(libs.gdx.core)
    nativesDesktop(libs.gdx.platform)
    implementation(libs.gdx.backend.lwjgl3)
}

fun DependencyHandlerScope.nativesDesktop(
    provider: Provider<MinimalExternalModuleDependency>,
) = runtimeOnly(variantOf(provider) { classifier("natives-desktop") })
