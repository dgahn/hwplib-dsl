package me.dgahn

interface TagConsumer<out R> {
    fun onTagStart(tag: Tag)
    fun onTagAttributeChange(tag: Tag, attribute: String, value: String?)
    fun onTagEnd(tag: Tag)
    fun onTagContent(content: CharSequence)
    fun onTagContentUnsafe(block: Unsafe.() -> Unit)
    fun onTagComment(content: CharSequence)
    fun onTagError(tag: Tag, exception: Throwable): Unit = throw exception
    fun finalize(): R
}

interface Tag {
    val tagName: String
    val consumer: TagConsumer<*>
    val namespace: String?

    val attributes: MutableMap<String, String>
    val attributesEntries: Collection<Map.Entry<String, String>>

    val inlineTag: Boolean
    val emptyTag: Boolean

    operator fun String.unaryPlus(): Unit {
        text(this)
    }

    fun text(s: String) {
        consumer.onTagContent(s)
    }

    fun text(n: Number) {
        text(n.toString())
    }

    fun comment(s: String) {
        consumer.onTagComment(s)
    }
}

interface Unsafe {
    operator fun String.unaryPlus()

    fun raw(s: String) {
        +s
    }

    fun raw(n: Number) {
        +n.toString()
    }
}

interface AttributeEnum {
    val realValue: String
}

fun <T : Tag> T.visit(block: T.() -> Unit) {
    consumer.onTagStart(this)
    try {
        this.block()
    } catch (err: Throwable) {
        consumer.onTagError(this, err)
    } finally {
        consumer.onTagEnd(this)
    }
}

fun <T : Tag, R> T.visitAndFinalize(consumer: TagConsumer<R>, block: T.() -> Unit): R {
    if (this.consumer !== consumer) {
        throw IllegalArgumentException("Wrong exception")
    }

    visit(block)
    return consumer.finalize()
}

fun attributesMapOf() = emptyMap
fun attributesMapOf(key: String, value: String?): Map<String, String> = when (value) {
    null -> emptyMap
    else -> singletonMapOf(key, value)
}

fun attributesMapOf(vararg pairs: String?): Map<String, String> {
    var result: MutableMap<String, String>? = null

    for (i in 0..pairs.size - 1 step 2) {
        val k = pairs[i]
        val v = pairs[i + 1]
        if (k != null && v != null) {
            if (result == null) {
                result = linkedMapOf()
            }
            result.put(k, v)
        }
    }

    return result ?: emptyMap
}

fun singletonMapOf(key: String, value: String): Map<String, String> = SingletonStringMap(key, value)

val emptyMap: Map<String, String> = emptyMap()

class DefaultUnsafe : Unsafe {
    private val sb = StringBuilder()

    override fun String.unaryPlus() {
        sb.append(this)
    }

    override fun toString(): String = sb.toString()
}

private data class SingletonStringMap(override val key: String, override val value: String) : Map<String, String>, Map.Entry<String, String> {
    override val entries: Set<Map.Entry<String, String>>
        get() = setOf(this)

    override val keys: Set<String>
        get() = setOf(key)

    override val size: Int
        get() = 1

    override val values: Collection<String>
        get() = listOf(value)

    override fun containsKey(key: String) = key == this.key
    override fun containsValue(value: String) = value == this.value
    override fun get(key: String): String? = if (key == this.key) value else null
    override fun isEmpty() = false
}