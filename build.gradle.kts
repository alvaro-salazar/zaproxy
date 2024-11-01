import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions") version "0.51.0"
    id("net.ltgt.errorprone") version "4.0.0"
    id("org.sonarqube") version "5.1.0.4882"
}

apply(from = "$rootDir/gradle/ci.gradle.kts")

subprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "org.zaproxy.common")
    apply(plugin = "net.ltgt.errorprone")

    spotless {
        kotlinGradle {
            ktlint()
        }
    }

    project.plugins.withType(JavaPlugin::class) {
        dependencies {
            "errorprone"("com.google.errorprone:error_prone_core:2.28.0")
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.errorprone {
            disableAllChecks.set(true)
            error(
                "MissingOverride",
                "WildcardImport",
            )
        }
    }
}

sonar {
  properties {
    property("sonar.projectKey", "alvaro-salazar_zaproxy")
    property("sonar.organization", "alvaro-salazar")
    property("sonar.host.url", "https://sonarcloud.io")
  }
}
