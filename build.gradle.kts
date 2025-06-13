import me.modmuss50.mpp.ReleaseType
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import java.io.ByteArrayOutputStream


plugins {
    java
    `java-library`
    id("com.diffplug.spotless") version "7.0.3"
    id("io.papermc.hangar-publish-plugin") version "0.1+"
    id("io.papermc.paperweight.userdev") version "2.0.0+"
    id("me.modmuss50.mod-publish-plugin") version "0.8+"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

val pluginChannel = System.getenv("CHANNEL") ?: "Snapshot"
val pluginLatestCommitMessage = gitCmd("log", "-1", "--pretty=%B")
val pluginSnapshotVersion = (project.version as String) + "+git_" + gitCmd("rev-parse", "--short", "HEAD")

println("Plugin version: $pluginSnapshotVersion")
println("Plugin latest commit: $pluginLatestCommitMessage")
println("Plugin channel: $pluginChannel")
println("Plugin release type: ${getReleaseType()}")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.jar {
    from(".") {
        include("LICENSE")
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
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")

    implementation("io.vavr:vavr:0.10.6")
    implementation("one.util:streamex:0.8.3")

    compileOnly("org.jetbrains:annotations:26.0.2")

    annotationProcessor("org.projectlombok:lombok:1.18.38")
    compileOnly("org.projectlombok:lombok:1.18.38")
}

tasks {
    runServer {
        minecraftVersion("1.21")
    }
}

hangarPublish {
    if (System.getenv("CHANNEL") == "Release") {
        publications.register("pluginRelease") {
            version = (project.version as String)
            id = "PDCInspector"
            channel = "Release"

            apiKey = System.getenv("HANGAR_API_TOKEN")

            platforms {
                paper {
                    jar = tasks.jar.flatMap { it.archiveFile }
                    platformVersions = listOf("1.21-1.21.5")
                }
            }
        }
    }

    publications.register("pluginSnapshotWithCommit") {
        version = pluginSnapshotVersion
        id = "PDCInspector"
        channel = "Snapshot"
        changelog = pluginLatestCommitMessage

        apiKey = System.getenv("HANGAR_API_TOKEN")

        platforms {
            paper {
                jar = tasks.jar.flatMap { it.archiveFile }
                platformVersions = listOf("1.21-1.21.5")
            }
        }
    }
}

publishMods {
    file = tasks.jar.get().archiveFile
    type = getReleaseType()
    modLoaders = listOf("paper", "folia")
    changelog = pluginLatestCommitMessage

    if (pluginChannel == "Snapshot") {
        version = pluginSnapshotVersion
    } else {
        version = project.version as String
    }

    // Only on release
    if (System.getenv("CHANNEL") == "Release") {
        github {
            accessToken = System.getenv("GH_API_TOKEN")
            repository = "Krysztal112233/PDCInspector"
            commitish = "main"
        }
    }

    modrinth {
        accessToken = System.getenv("MODRINTH_API_TOKEN")
        projectId = "12wagq22"
        minecraftVersionRange {
            start = project.findProperty("fromMinecraftVersion") as String
            end = project.findProperty("toMinecraftVersion") as String

            includeSnapshots = true
        }
    }
}

fun gitCmd(vararg command: String): String {
    val byteOut = ByteArrayOutputStream()
    exec {
        commandLine = listOf("git", *command)
        standardOutput = byteOut
    }
    return byteOut.toString(Charsets.UTF_8.name()).trim()
}

fun getReleaseType(): ReleaseType =
    when (pluginChannel.toDefaultLowerCase()) {
        "release" -> ReleaseType.STABLE
        else -> ReleaseType.ALPHA
    }
