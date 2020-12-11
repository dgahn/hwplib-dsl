package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.writer.HWPWriter

class HwpStreamBuilder<O : HWPFile>(override val hwpFile: O) : TagConsumer<O> {

    override fun onTagStart(tag: Tag) {
        tag.builder.build()
    }

    override fun initTagProperty(tag: Tag) {
        when (tag) {
            is TABLE -> tag.initControl(tag.builder.control)
            is TD -> tag.initCell(tag.builder.cell)
        }
    }

    override fun onTagText(content: CharSequence) {
        val s = hwpFile.bodyText.sectionList.first()
        val firstParagraph = s.getParagraph(0)
        firstParagraph.text.addString(content.toString())
    }

    override fun onTagEnd(tag: Tag) = Unit

    override fun finalize(): O = hwpFile
}

fun <O : HWPFile> O.createHwp(): TagConsumer<O> = HwpStreamBuilder(this)

fun HWPFile.build(path: String) = HWPWriter.toFile(this, path)

fun readHwp(path: String): HWPFile = HWPReader.fromFile(path)
