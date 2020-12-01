package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine
import kr.dogfoot.hwplib.writer.HWPWriter
import java.awt.image.BufferedImage

fun HWPFile.hwp(path: String, block: HWPFile.() -> Unit) = block.invoke(this).also {
    HWPWriter.toFile(this, path)
}

fun HWPFile.body(block: HWPFile.() -> Unit) = block.invoke(this)

fun HWPFile.paperSize(paperSize: PaperSize) {
    val csd = this.bodyText.sectionList.first().getParagraph(0).controlList.first() as ControlSectionDefine
    val size = when(paperSize) {
        PaperSize.B5 ->  Size(51592, 72852)
        PaperSize.B4 -> Size(72852, 103180)
        PaperSize.A4 -> Size(59528, 84188)
        PaperSize.A3 -> Size(84188, 119052)
        PaperSize.LEGAL -> Size(61200, 100800)
        PaperSize.A5 -> Size(41976, 59528)
    }
    csd.pageDef.paperWidth = size.width
    csd.pageDef.paperHeight = size.height
}

enum class PaperSize {
    B5,
    B4,
    A4,
    A3,
    LEGAL,
    A5
}


data class Size(
    val width: Long,
    val height: Long
)