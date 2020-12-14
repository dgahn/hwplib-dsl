package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile

fun HWP.body(block: BODY.() -> Unit = {}): Unit = BODY(consumer, BodyBuilder(consumer.hwpFile)).visit(block)

open class BODY(
    override val consumer: HwpTagConsumer<*>,
    override val builder: BodyBuilder
) : Tag

class BodyBuilder(override val hwpFile: HWPFile) : HwpTagBuilder {
    override fun build() = Unit
    override fun completed() = Unit
}
