package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.charshape.ParaCharShape
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.header.ParaHeader
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.lineseg.ParaLineSeg
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText
import java.io.UnsupportedEncodingException

fun setParaHeader(p: Paragraph) {
    val ph: ParaHeader = p.header
    ph.isLastInList = true
    // 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
    ph.paraShapeId = 1
    // 셀의 스타일을이미 만들어진 스타일으로 사용함
    ph.styleId = 1.toShort()
    ph.divideSort.isDivideSection = false
    ph.divideSort.isDivideMultiColumn = false
    ph.divideSort.isDividePage = false
    ph.divideSort.isDivideColumn = false
    ph.charShapeCount = 1
    ph.rangeTagCount = 0
    ph.lineAlignCount = 1
    ph.instanceID = 0
    ph.isMergedByTrack = 0
}

fun setParaText(p: Paragraph, text: String) {
    if (p.text != null) {
        p.deleteText()
    }
    p.createText()
    val pt: ParaText = p.text
    try {
        pt.addString(text)
    } catch (e: UnsupportedEncodingException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
}

fun setParaCharShape(p: Paragraph) {
    if (p.charShape != null) {
        p.deleteCharShape()
    }
    p.createCharShape()
    val pcs: ParaCharShape = p.charShape
    // 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
    pcs.addParaCharShape(0, 1)
}

fun setParaLineSeg(p: Paragraph) {
    if (p.lineSeg != null) {
        p.deleteLineSeg()
    }
    p.createLineSeg()
    val pls: ParaLineSeg = p.lineSeg
    val lsi = pls.addNewLineSegItem()
    lsi.textStartPositon = 0
    lsi.lineVerticalPosition = 0
    lsi.lineHeight = ptToLineHeight(10.0)
    lsi.textPartHeight = ptToLineHeight(10.0)
    lsi.distanceBaseLineToLineVerticalPosition = ptToLineHeight(10.0 * 0.85)
    lsi.lineSpace = ptToLineHeight(3.0)
    lsi.startPositionFromColumn = 0
    lsi.segmentWidth = mmToHwp(50.0).toInt()
    lsi.tag.firstSegmentAtLine = true
    lsi.tag.lastSegmentAtLine = true
}

fun ptToLineHeight(pt: Double): Int {
    return (pt * 100.0f).toInt()
}

fun mmToHwp(mm: Double): Long {
    return (mm * 72000.0f / 254.0f + 0.5f).toLong()
}