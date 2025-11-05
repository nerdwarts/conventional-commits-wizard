plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.7.1"
}

group = "de.nerdwarts"
version = "1.0.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure IntelliJ Platform Gradle Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1.4.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "231"
        }

        changeNotes = """
                <h3>1.0.1</h3>
                <ul>
                    <li>Added custom toolbar icon for Commit Wizard action</li>
                    <li>Improved plugin compatibility across JetBrains IDEs (IntelliJ IDEA, PyCharm, GoLand, RustRover)</li>
                    <li>Fixed icon display issue in VCS toolbar</li>
                </ul>
                
                <h3>1.0.0</h3>
                <ul>
                    <li>Initial release</li>
                    <li>Interactive wizard for creating conventional commit messages</li>
                    <li>Support for standard commit types (feat, fix, docs, style, refactor, test, chore)</li>
                    <li>Optional scope and breaking change indicators</li>
                    <li>Emoji support with configurable toggle</li>
                    <li>Integration with VCS commit dialog</li>
                </ul>
        """.trimIndent()
    }
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        sinceBuild.set("231")
    }
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
    jvmToolchain(21)
}
