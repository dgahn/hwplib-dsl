object KotestVersion {
    const val kotest = "4.3.0"
}

object KotestLibs {
    const val runnerJunit5 = "io.kotest:kotest-runner-junit5:${KotestVersion.kotest}"
    const val assertionsCore = "io.kotest:kotest-assertions-core:${KotestVersion.kotest}"
    const val kotestProperty = "io.kotest:kotest-property:${KotestVersion.kotest}"
}