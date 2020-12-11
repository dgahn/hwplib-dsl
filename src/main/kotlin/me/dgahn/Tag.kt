package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile

interface TagConsumer<out R> {

    val hwpFile: HWPFile

    fun onTagStart(tag: Tag)
    fun initTagProperty(tag: Tag)
    fun onTagText(content: CharSequence)
    fun onTagEnd(tag: Tag)
    fun onTagError(tag: Tag, exception: Throwable): Unit = throw exception
    fun finalize(): R
}

interface Tag {
    val consumer: TagConsumer<*>
    val builder: HwpTagBuilder

    operator fun String.unaryPlus(): Unit {
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
    consumer.initTagProperty(this)
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
