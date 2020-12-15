package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.Section
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.writer.HWPWriter

interface HwpTagConsumer<O : HWPFile> : TagConsumer<O> {
    val hwpFile: HWPFile
    var currentSection: Section

    fun initTagProperty(tag: Tag)
}

class HwpStreamBuilder<O : HWPFile>(override val hwpFile: O) : HwpTagConsumer<O> {

    override var currentSection: Section = hwpFile.bodyText.sectionList.first()
    var paragraphStyle: ParagraphStyle = ParagraphStyle()

    override fun onTagStart(tag: Tag) {
        tag.builder.build()
        initTagProperty(tag)
    }

    override fun initTagProperty(tag: Tag) {
        when (tag) {
            is TABLE -> tag.initControl(tag.builder.control)
            is TD -> tag.initCell(tag.builder.cell)
        }
    }

    override fun onTagText(content: CharSequence) {
        currentSection.addNewParagraph().apply {
            setParagraph(hwpFile = hwpFile, content = content.toString(), paragraphStyle = paragraphStyle)
        }
    }

    override fun onTagEnd(tag: Tag) {
        tag.builder.completed()
    }

    override fun finalize(): O = hwpFile


}

fun <O : HWPFile> O.createHwp(): HwpTagConsumer<O> = HwpStreamBuilder(this)

fun HWPFile.build(path: String) = HWPWriter.toFile(this, path)

fun readHwp(path: String): HWPFile = HWPReader.fromFile(path)
