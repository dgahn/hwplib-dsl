package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile

open class SECTION(
    override val consumer: HwpTagConsumer<*>,
    override val builder: SectionBuilder
) : Tag

fun BODY.section(isNewPage: Boolean = false, block: SECTION.() -> Unit = {}) {
    val bodyText = consumer.hwpFile.bodyText
    consumer.currentSection = bodyText.addNewSection()

    if(isNewPage) {
        consumer.currentSection.addNewParagraph().apply {
            setParagraph(hwpFile = consumer.hwpFile, content = "", paragraphStyle = ParagraphStyle(
                isDividePage = true
            ))
        }
    }

    SECTION(
        consumer = consumer,
        builder = SectionBuilder(hwpFile = consumer.hwpFile)
    ).visit(block)
}

class SectionBuilder(override val hwpFile: HWPFile) : HwpTagBuilder {
    override fun build() = Unit
    override fun completed() = Unit
}