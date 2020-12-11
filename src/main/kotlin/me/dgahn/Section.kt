package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile

open class SECTION(
    override val consumer: HwpTagConsumer<*>,
    override val builder: SectionBuilder
) : Tag

fun BODY.section(block: SECTION.() -> Unit = {}) {
    val bodyText = consumer.hwpFile.bodyText
    if (!consumer.isFirstSection) consumer.currentSection = bodyText.addNewSection()

    SECTION(
        consumer = consumer,
        builder = SectionBuilder(hwpFile = consumer.hwpFile)
    ).visit(block)
}

class SectionBuilder(override val hwpFile: HWPFile) : HwpTagBuilder {
    override fun build() = Unit
}