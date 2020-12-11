package me.dgahn

interface TagConsumer<out R> {
    fun onTagStart(tag: Tag)
    fun onTagText(content: CharSequence)
    fun onTagEnd(tag: Tag)
    fun onTagError(tag: Tag, exception: Throwable): Unit = throw exception
    fun finalize(): R
}

interface TagBuilder {
    fun build()
}

interface Tag {
    val consumer: TagConsumer<*>
    val builder: TagBuilder

    operator fun String.unaryPlus() {
        text(this)
    }

    fun text(s: String) {
        consumer.onTagText(s)
    }

    fun text(n: Number) {
        text(n.toString())
    }
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
