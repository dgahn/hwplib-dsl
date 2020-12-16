package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine

fun HWPFile.paperSize(
    paperSize: PaperSize,
    paperStyle: PaperStyle = PaperStyle()
) = apply {
    val s = bodyText.sectionList.first()
    val csd = s.getParagraph(0).controlList.first() as ControlSectionDefine
    val size = when (paperSize) {
        PaperSize.B5 -> Size(51592, 72852)
        PaperSize.B4 -> Size(72852, 103180)
        PaperSize.A4 -> Size(59528, 84188)
        PaperSize.A3 -> Size(84188, 119052)
        PaperSize.LEGAL -> Size(61200, 100800)
        PaperSize.A5 -> Size(41976, 59528)
    }
    csd.pageDef.leftMargin = mmToHwp(paperStyle.leftMargin)
    csd.pageDef.rightMargin = mmToHwp(paperStyle.rightMargin)
    csd.pageDef.topMargin = mmToHwp(paperStyle.topMargin)
    csd.pageDef.bottomMargin = mmToHwp(paperStyle.bottomMargin)
    csd.pageDef.headerMargin = mmToHwp(paperStyle.headerMargin)
    csd.pageDef.footerMargin = mmToHwp(paperStyle.footerMargin)
    csd.pageDef.gutterMargin = mmToHwp(paperStyle.gutterMargin)
    csd.pageDef.paperWidth = size.width
    csd.pageDef.paperHeight = size.height
    csd.footNoteShape.divideLineSort = paperStyle.footNoteShapeDivideLineSort
    csd.footNoteShape.divideLineThickness = paperStyle.footNoteShapeDivideLineThickness
    csd.endNoteShape.divideLineSort = paperStyle.endNoteShapeDivideLineSort
    csd.endNoteShape.divideLineThickness = paperStyle.endNoteShapeDivideLineThickness
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