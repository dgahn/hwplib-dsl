package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlColumnDefine
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderThickness
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType

fun HWPFile.paperSize(paperSize: PaperSize) = apply {
    val s = bodyText.addNewSection()
    val p = s.addNewParagraph()
    val csd = p.addNewControl(ControlType.SectionDefine) as ControlSectionDefine
    val ccd = p.addNewControl(ControlType.ColumnDefine) as ControlColumnDefine
    val size = when (paperSize) {
        PaperSize.B5 -> Size(51592, 72852)
        PaperSize.B4 -> Size(72852, 103180)
        PaperSize.A4 -> Size(59528, 84188)
        PaperSize.A3 -> Size(84188, 119052)
        PaperSize.LEGAL -> Size(61200, 100800)
        PaperSize.A5 -> Size(41976, 59528)
    }
    csd.pageDef.paperWidth = size.width
    csd.pageDef.paperHeight = size.height
    csd.footNoteShape.divideLineSort = BorderType.None
    csd.footNoteShape.divideLineThickness = BorderThickness.MM0_5
    csd.endNoteShape.divideLineSort = BorderType.None
    csd.endNoteShape.divideLineThickness = BorderThickness.MM0_5
    ccd.header.divideLineSort = BorderType.None
    ccd.header.divideLineThickness = BorderThickness.MM0_5
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