object LogVersion {
    const val logback = "1.2.3"
    const val kotlinLogging = "2.0.3"
}

object LogLibs {
    const val logback = "ch.qos.logback:logback-classic:${LogVersion.logback}"
    const val kotlinLogging = "io.github.microutils:kotlin-logging:${LogVersion.kotlinLogging}"
}
