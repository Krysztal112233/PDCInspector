plugins {
    java
    `java-library`
    id("com.diffplug.spotless") version "7.0.3"
    id("com.github.spotbugs") version "6.1.7"
    id("io.papermc.paperweight.userdev") version "2.0.0+"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
repositories {
    mavenCentral()
}

spotless {
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }

    java {
        target("**/*.java")
        targetExclude("**/package-info.java")

        importOrder()
        removeUnusedImports()

        formatAnnotations()

        leadingTabsToSpaces(4)

        licenseHeaderFile("HEADERFILE")
    }
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    implementation("io.vavr:vavr:0.10.6")
    implementation("one.util:streamex:0.8.3")

    compileOnly("org.jetbrains:annotations:26.0.2")

    annotationProcessor("org.projectlombok:lombok:1.18.38")
    compileOnly("org.projectlombok:lombok:1.18.38")
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
    }
}
