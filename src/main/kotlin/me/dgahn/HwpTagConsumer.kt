package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.Section
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.writer.HWPWriter

interface HwpTagConsumer<O : HWPFile> : TagConsumer<O> {
    val hwpFile: HWPFile
    var currentSection: Section
    var isFirstSection: Boolean

    fun initTagProperty(tag: Tag)
}

class HwpStreamBuilder<O : HWPFile>(override val hwpFile: O) : HwpTagConsumer<O> {

    override var currentSection: Section = hwpFile.bodyText.sectionList.first()
    override var isFirstSection: Boolean = true
    private var isFirstPara: Boolean = true

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
        if (isFirstSection && isFirstPara) {
            val p = currentSection.getParagraph(0)
            p.text.addString(content.toString())
            isFirstPara = false
        } else {
            val p = currentSection.addNewParagraph()
            setParaHeader(p)
            setParaText(p, content.toString())
            setParaCharShape(p)
            setParaLineSeg(p)
        }
    }

    override fun onTagEnd(tag: Tag) {
        if (tag is SECTION && isFirstSection) {
            isFirstSection = false
        }
    }

    override fun finalize(): O = hwpFile


}

fun <O : HWPFile> O.createHwp(): HwpTagConsumer<O> = HwpStreamBuilder(this)

fun HWPFile.build(path: String) = HWPWriter.toFile(this, path)

fun readHwp(path: String): HWPFile = HWPReader.fromFile(path)
