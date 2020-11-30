object KotlinPlugin {
    const val spring = "plugin.spring"
    const val kotlin = "kotlin"
    const val jvm = "jvm"
}

object KotlinVersion {
    const val kotlin = "1.4.10"
    const val coroutine = "1.4.1"
}

object KotlinLibs {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinVersion.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"

    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${KotlinVersion.coroutine}"
    const val coroutinesReactive = "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${KotlinVersion.coroutine}"
    const val coroutinesReactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${KotlinVersion.coroutine}"
    const val coroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${KotlinVersion.coroutine}"

}