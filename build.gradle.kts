plugins {
    kotlin(KotlinPlugin.jvm) version KotlinVersion.kotlin
}

allprojects {
    apply(plugin = KotlinPlugin.kotlin)
    group = "me.dgahn"
    version = "0.1.0"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(LogLibs.kotlinLogging)
        implementation(LogLibs.logback)

        testImplementation(KotestLibs.runnerJunit5)
        testImplementation(KotestLibs.assertionsCore)
        testImplementation(KotestLibs.kotestProperty)
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
