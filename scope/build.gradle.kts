@Suppress(
    // known false positive: https://youtrack.jetbrains.com/issue/KTIJ-19369
    "DSL_SCOPE_VIOLATION"
)
plugins {
    `java-library`
    `maven-publish`
    signing
    alias(libs.plugins.spotless)
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

spotless {
    isEnforceCheck = false
    java {
        palantirJavaFormat()
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}


dependencies {
    implementation(libs.gdx.core)
    implementation(libs.gdx.backend.lwjgl3)
    implementation(libs.imgui.binding)
    implementation(libs.imgui.lwgjl3)
    implementation(libs.imgui.natives.linux)
    implementation(libs.imgui.natives.macos)
    implementation(libs.imgui.natives.windows)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "scope"
            from(components["java"])
            pom {
                name.set("Scope")
                description.set("A debug UI utility")
                url.set("https://www.github.com/fourlastor-alexandria/scope")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/fourlastor-alexandria/scope/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("fourlastor")
                        name.set("Daniele Conti")
                    }
                }
                scm {
                    connection.set("scm:git:https://www.github.com/fourlastor-alexandria/scope.git")
                    developerConnection.set("scm:git:https://www.github.com/fourlastor-alexandria/scope.git")
                    url.set("https://www.github.com/fourlastor-alexandria/scope")
                }
            }
        }
    }
}

//signing {
//    val signingKey: String? by project
//    val signingPassword: String? by project
//    useInMemoryPgpKeys(signingKey, signingPassword)
//    sign(publishing.publications["mavenJava"])
//}
