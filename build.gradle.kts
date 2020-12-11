plugins {
    kotlin(KotlinPlugin.jvm) version KotlinVersion.kotlin
    id(LintPlugin.kotlinLinter) version LintVersion.kotlinLinter
    id(LintPlugin.detekt) version LintVersion.detekt
    id(DocPlugin.dokka) version DocVersion.dokka
}

allprojects {
    apply(plugin = KotlinPlugin.kotlin)
    apply(plugin = LintPlugin.kotlinLinter)
    apply(plugin = LintPlugin.detekt)
    apply(plugin = DocPlugin.dokka)

    group = "me.dgahn"
    version = "0.1.0"

    repositories {
        google()
        jcenter()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(LogLibs.kotlinLogging)
        implementation(LogLibs.logback)

        implementation(HwpLibs.hwpLib)

        testImplementation(KotestLibs.runnerJunit5)
        testImplementation(KotestLibs.assertionsCore)
        testImplementation(KotestLibs.kotestProperty)
    }

    kotlinter {
        ignoreFailures = false
        indentSize = 4
        reporters = arrayOf("checkstyle", "plain")
        experimentalRules = false
        disabledRules = arrayOf("ForbiddenComment")
    }

    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = "11"
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = "11"
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
