package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.header.ParaHeader
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.lineseg.ParaLineSeg
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText
import kr.dogfoot.hwplib.`object`.docinfo.FaceName
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

fun setParaCharShape(hwpFile: HWPFile, p: Paragraph, paragraphStyle: ParagraphStyle) {
    val paragraphStartPos = paragraphStyle.paragraphStartPos
    val boldStartPos = paragraphStyle.boldStartPos
    val boldEndPos = paragraphStyle.boldEndPos
    p.createCharShape()
    val pcs = p.charShape
    val faceNameId = createFaceName(hwpFile, paragraphStyle)
    pcs.addParaCharShape(paragraphStartPos.toLong(), faceNameId.toLong())
    pcs.addParaCharShape(boldStartPos.toLong(), createCharShape(hwpFile, faceNameId, paragraphStyle).toLong())
    pcs.addParaCharShape(boldEndPos.toLong(), createCharShape(hwpFile, faceNameId, paragraphStyle).toLong())
}

private fun createFaceName(hwpFile: HWPFile, paragraphStyle: ParagraphStyle): Int {

    // 한글 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hangul part.)
    var fn: FaceName = hwpFile.docInfo.addNewHangulFaceName()
    setFaceNameForBatang(fn, paragraphStyle)

    // 영어 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for english part.)
    fn = hwpFile.docInfo.addNewEnglishFaceName()
    setFaceNameForBatang(fn, paragraphStyle)

    // 한자 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hanja(Chinese)
    // part.)
    fn = hwpFile.docInfo.addNewHanjaFaceName()
    setFaceNameForBatang(fn, paragraphStyle)

    // 일본어 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for japanse part.)
    fn = hwpFile.docInfo.addNewJapaneseFaceName()
    setFaceNameForBatang(fn, paragraphStyle)

    // 기타 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for etc part.)
    fn = hwpFile.docInfo.addNewEtcFaceName()
    setFaceNameForBatang(fn, paragraphStyle)

    // 기호 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for symbol part.)
    fn = hwpFile.docInfo.addNewSymbolFaceName()
    setFaceNameForBatang(fn, paragraphStyle)

    // 사용자 정의 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for user part.)
    fn = hwpFile.docInfo.addNewUserFaceName()
    setFaceNameForBatang(fn, paragraphStyle)
    return hwpFile.docInfo.hangulFaceNameList.size - 1
}

private fun setFaceNameForBatang(fn: FaceName, paragraphStyle: ParagraphStyle) {
    fn.property.setHasBaseFont(paragraphStyle.hasBaseFont)
    fn.property.setHasFontInfo(paragraphStyle.hasFontInfo)
    fn.property.setHasSubstituteFont(paragraphStyle.hasSubstituteFont)
    fn.name = paragraphStyle.fontName
}

private fun createCharShape(hwpFile: HWPFile, faceNameId: Int, paragraphStyle: ParagraphStyle): Int {
    val cs = hwpFile.docInfo.addNewCharShape()
    // 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
    cs.faceNameIds.setForAll(faceNameId)
    cs.ratios.setForAll(paragraphStyle.ratios)
    cs.charSpaces.setForAll(paragraphStyle.charSpaces)
    cs.relativeSizes.setForAll(paragraphStyle.relativeSizes)
    cs.charOffsets.setForAll(paragraphStyle.charOffsets)
    cs.baseSize = paragraphStyle.baseSize
    cs.property.isItalic = paragraphStyle.isItalic
    cs.property.isBold = paragraphStyle.isBold
    cs.property.underLineSort = paragraphStyle.underLineSort
    cs.property.outterLineSort = paragraphStyle.outterLineSort
    cs.property.shadowSort = paragraphStyle.shadowSort
    cs.property.isEmboss = paragraphStyle.isEmboss
    cs.property.isEngrave = paragraphStyle.isEngrave
    cs.property.isSuperScript = paragraphStyle.isSuperScript
    cs.property.isSubScript = paragraphStyle.isSubScript
    cs.property.isStrikeLine = paragraphStyle.isStrikeLine
    cs.property.emphasisSort = paragraphStyle.emphasisSort
    cs.property.isUsingSpaceAppropriateForFont = paragraphStyle.isUsingSpaceAppropriateForFont
    cs.property.strikeLineShape = paragraphStyle.strikeLineShape
    cs.property.isKerning = paragraphStyle.isKerning
    cs.shadowGap1 = paragraphStyle.shadowGap1
    cs.shadowGap2 = paragraphStyle.shadowGap2
    cs.charColor.value = paragraphStyle.charColorValue
    cs.underLineColor.value = paragraphStyle.underLineColorValue
    cs.shadeColor.value = paragraphStyle.shadeColorValue
    cs.shadowColor.value = paragraphStyle.shadowColorValue
    cs.borderFillId = paragraphStyle.borderFillId
    return hwpFile.docInfo.charShapeList.size - 1
}

fun ptToLineHeight(pt: Double): Int {
    return (pt * 100.0f).toInt()
}

fun mmToHwp(mm: Double): Long {
    return (mm * 72000.0f / 254.0f + 0.5f).toLong()
}