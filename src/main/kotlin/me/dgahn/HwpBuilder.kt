package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker
import kr.dogfoot.hwplib.writer.HWPWriter

class HwpStreamBuilder<O : HWPFile>(override val hwpFile: O) : TagConsumer<O> {

    override fun onTagStart(tag: Tag) {
        when(tag) {
            is IMG -> hwpFile.img(width = tag.width, height = tag.height, img = tag.src)
            is TABLE -> {
                val control = hwpFile.table(tag.rowCount, tag.colCount)
                tag.initControl(control)
            }
            is TD -> tag.td(hwpFile)
        }
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) = Unit

    override fun onTagEnd(tag: Tag) = Unit

    override fun onTagText(content: CharSequence) {
        val s = hwpFile.bodyText.sectionList.first()
        val firstParagraph = s.getParagraph(0)
        firstParagraph.text.addString(content.toString())
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) = Unit

    override fun onTagComment(content: CharSequence) = Unit

    override fun finalize(): O = hwpFile

}

fun <O : HWPFile> O.createHwp(): TagConsumer<O> = HwpStreamBuilder(this)

fun HWPFile.build(path: String) = HWPWriter.toFile(this, path)

fun readHwp(path: String): HWPFile = HWPReader.fromFile(path)