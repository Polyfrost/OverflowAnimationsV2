plugins {
    id("dev.kikugie.stonecutter")
    id("com.diffplug.spotless") version "8.2.1"
}

stonecutter active "26.4" /* [SC] DO NOT EDIT */

stonecutter tasks {
    order("publishMods")
}

stonecutter parameters {
    replacements {
        // Mojang mappings are the official names from 26.1 onwards
        string(current.parsed >= "26.1") {
            replace("accessWidener v2 named", "accessWidener v2 official")
        }
    }
}

// Header
spotless {
    val licenseHeader = rootProject.file("HEADER")
    lineEndings = com.diffplug.spotless.LineEnding.UNIX

    java {
        licenseHeaderFile(licenseHeader)
        target("src/**/*.java")
    }

    kotlin {
        licenseHeaderFile(licenseHeader)
        target("src/**/*.kt")
    }
}
