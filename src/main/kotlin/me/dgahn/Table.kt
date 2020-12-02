package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlTable
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HeightCriterion
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HorzRelTo
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.ObjectNumberSort
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.RelativeArrange
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextFlowMethod
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextHorzArrange
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.VertRelTo
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.WidthCriterion
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.sectiondefine.TextDirection
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.textbox.LineChange
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.textbox.TextVerticalAlignment
import kr.dogfoot.hwplib.`object`.bodytext.control.table.Cell
import kr.dogfoot.hwplib.`object`.bodytext.control.table.DivideAtPageBoundary
import kr.dogfoot.hwplib.`object`.bodytext.control.table.ListHeaderForCell
import kr.dogfoot.hwplib.`object`.bodytext.control.table.Row
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.charshape.ParaCharShape
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.header.ParaHeader
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.lineseg.ParaLineSeg
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BackSlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderThickness
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.SlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PatternType
import java.io.UnsupportedEncodingException


fun HWPFile.table(
    rowCount: Int,
    colCount: Int,
    block: ControlTable.() -> Unit = {}
) {
    val section = this.bodyText.sectionList.first()
    val paragraph = section.getParagraph(0)

    paragraph.text.addExtendCharForTable()
    paragraph.addNewControl(ControlType.Table).let {
        val controlTable = it as ControlTable
        block.invoke(controlTable)
        style(rowCount, colCount, this, it)
    }
}

private fun style(
    rowCount: Int,
    colCount: Int,
    hwpFile: HWPFile,
    controlTable: ControlTable,
    isLikeWord: Boolean = true,
    isApplyLineSpace: Boolean = false,
    vertRelTo: VertRelTo = VertRelTo.Para,
    vertRelativeArrange: RelativeArrange = RelativeArrange.TopOrLeft,
    horzRelTo: HorzRelTo = HorzRelTo.Para,
    horzRelativeArrange: RelativeArrange = RelativeArrange.TopOrLeft,
    isVertRelToParaLimit: Boolean = false,
    isAllowOverlap: Boolean = false,
    widthCriterion: WidthCriterion = WidthCriterion.Absolute,
    heightCriterion: HeightCriterion = HeightCriterion.Absolute,
    isProtectSize: Boolean = false,
    textFlowMethod: TextFlowMethod = TextFlowMethod.Tight,
    textHorzArrange: TextHorzArrange = TextHorzArrange.BothSides,
    objectNumberSort: ObjectNumberSort = ObjectNumberSort.Table,
    offsetX: Double = 20.0,
    offsetY: Double = 20.0,
    width: Double = 100.0,
    height: Double = 60.0,
    zOrder: Int = 1,
    outterMarginLeft: Int = 10,
    outterMarginRight: Int = 10,
    outterMarginTop: Int = 100,
    outterMarginBottom: Int = 100,
) {
    val ctrlHeader = controlTable.header
    ctrlHeader.property.isLikeWord = isLikeWord
    ctrlHeader.property.isApplyLineSpace = isApplyLineSpace
    ctrlHeader.property.vertRelTo = vertRelTo
    ctrlHeader.property.vertRelativeArrange = vertRelativeArrange
    ctrlHeader.property.horzRelTo = horzRelTo
    ctrlHeader.property.horzRelativeArrange = horzRelativeArrange
    ctrlHeader.property.isVertRelToParaLimit = isVertRelToParaLimit
    ctrlHeader.property.isAllowOverlap = isAllowOverlap
    ctrlHeader.property.widthCriterion = widthCriterion
    ctrlHeader.property.heightCriterion = heightCriterion
    ctrlHeader.property.isProtectSize = isProtectSize
    ctrlHeader.property.textFlowMethod = textFlowMethod
    ctrlHeader.property.textHorzArrange = textHorzArrange
    ctrlHeader.property.objectNumberSort = objectNumberSort
    ctrlHeader.setxOffset(mmToHwp(offsetX))
    ctrlHeader.setyOffset(mmToHwp(offsetY))
    ctrlHeader.width = mmToHwp(width)
    ctrlHeader.height = mmToHwp(height)
    ctrlHeader.setzOrder(zOrder)
    ctrlHeader.outterMarginLeft = outterMarginLeft
    ctrlHeader.outterMarginRight = outterMarginRight
    ctrlHeader.outterMarginTop = outterMarginTop
    ctrlHeader.outterMarginBottom = outterMarginBottom

    val tableRecord = controlTable.table
    tableRecord.property.divideAtPageBoundary = DivideAtPageBoundary.DivideByCell
    tableRecord.property.isAutoRepeatTitleRow = false
    tableRecord.rowCount = rowCount
    tableRecord.columnCount = colCount
    tableRecord.cellSpacing = 0
    tableRecord.leftInnerMargin = 0
    tableRecord.rightInnerMargin = 0
    tableRecord.topInnerMargin = 0
    tableRecord.bottomInnerMargin = 0
    tableRecord.borderFillId = getBorderFillIDForTableOutterLine(hwpFile) // 얘 문제 아님
    tableRecord.cellCountOfRowList.add(rowCount)
    tableRecord.cellCountOfRowList.add(colCount)
}

private fun getBorderFillIDForTableOutterLine(hwpFile: HWPFile): Int {
    val bf = hwpFile.docInfo.addNewBorderFill()
    bf.property.is3DEffect = false
    bf.property.isShadowEffect = false
    bf.property.slashDiagonalShape = SlashDiagonalShape.None
    bf.property.backSlashDiagonalShape = BackSlashDiagonalShape.None
    bf.leftBorder.type = BorderType.None
    bf.leftBorder.thickness = BorderThickness.MM0_5
    bf.leftBorder.color.value = 0x0
    bf.rightBorder.type = BorderType.None
    bf.rightBorder.thickness = BorderThickness.MM0_5
    bf.rightBorder.color.value = 0x0
    bf.topBorder.type = BorderType.None
    bf.topBorder.thickness = BorderThickness.MM0_5
    bf.topBorder.color.value = 0x0
    bf.bottomBorder.type = BorderType.None
    bf.bottomBorder.thickness = BorderThickness.MM0_5
    bf.bottomBorder.color.value = 0x0
    bf.diagonalSort = BorderType.None
    bf.diagonalThickness = BorderThickness.MM0_5
    bf.diagonalColor.value = 0x0
    bf.fillInfo.type.setPatternFill(true)
    bf.fillInfo.createPatternFill()
    val pf = bf.fillInfo.patternFill
    pf.patternType = PatternType.None
    pf.backColor.value = -1
    pf.patternColor.value = 0
    return hwpFile.docInfo.borderFillList.size
}

private fun getBorderFillIDForCell(hwpFile: HWPFile): Int {
    val bf = hwpFile.docInfo.addNewBorderFill()
    bf.property.is3DEffect = false
    bf.property.isShadowEffect = false
    bf.property.slashDiagonalShape = SlashDiagonalShape.None
    bf.property.backSlashDiagonalShape = BackSlashDiagonalShape.None
    bf.leftBorder.type = BorderType.Solid
    bf.leftBorder.thickness = BorderThickness.MM0_5
    bf.leftBorder.color.value = 0x0
    bf.rightBorder.type = BorderType.Solid
    bf.rightBorder.thickness = BorderThickness.MM0_5
    bf.rightBorder.color.value = 0x0
    bf.topBorder.type = BorderType.Solid
    bf.topBorder.thickness = BorderThickness.MM0_5
    bf.topBorder.color.value = 0x0
    bf.bottomBorder.type = BorderType.Solid
    bf.bottomBorder.thickness = BorderThickness.MM0_5
    bf.bottomBorder.color.value = 0x0
    bf.diagonalSort = BorderType.None
    bf.diagonalThickness = BorderThickness.MM0_5
    bf.diagonalColor.value = 0x0
    bf.fillInfo.type.setPatternFill(true)
    bf.fillInfo.createPatternFill()
    val pf = bf.fillInfo.patternFill
    pf.patternType = PatternType.None
    pf.backColor.value = -1
    pf.patternColor.value = 0
    return hwpFile.docInfo.borderFillList.size
}

fun ControlTable.tr(
    block: Row.() -> Unit = {}
): ControlTable = this.also {
    block.invoke(this.addNewRow())
}

fun Row.td(
    hwpFile: HWPFile,
    row: Int,
    col: Int,
    text: String,
    block: Row.() -> Unit = {}
) {
    val borderFillIDForCell = getBorderFillIDForCell(hwpFile)
    val cell = this.addNewCell()
    setListHeaderForCell(col, row, cell, borderFillIDForCell)
    setParagraphForCell(text, cell)
    block.invoke(this)
}

private fun setListHeaderForCell(colIndex: Int, rowIndex: Int, cell: Cell, borderFillIDForCell: Int) {
    val lh: ListHeaderForCell = cell.listHeader
    lh.paraCount = 1
    lh.property.textDirection = TextDirection.Horizontal
    lh.property.lineChange = LineChange.Normal
    lh.property.textVerticalAlignment = TextVerticalAlignment.Center
    lh.property.isProtectCell = false
    lh.property.isEditableAtFormMode = false
    lh.colIndex = colIndex
    lh.rowIndex = rowIndex
    lh.colSpan = 1
    lh.rowSpan = 1
    lh.width = mmToHwp(50.0)
    lh.height = mmToHwp(30.0)
    lh.leftMargin = 0
    lh.rightMargin = 0
    lh.topMargin = 0
    lh.bottomMargin = 0
    lh.borderFillId = borderFillIDForCell
    lh.textWidth = mmToHwp(50.0)
    lh.fieldName = ""
}

private fun setParagraphForCell(text: String, cell: Cell) {
    val p: Paragraph = cell.paragraphList.addNewParagraph()
    setParaHeader(p)
    setParaText(p, text)
    setParaCharShape(p)
    setParaLineSeg(p)
}

private fun setParaHeader(p: Paragraph) {
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

private fun setParaText(p: Paragraph, text2: String) {
    p.createText()
    val pt: ParaText = p.text
    try {
        pt.addString(text2)
    } catch (e: UnsupportedEncodingException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
}

private fun setParaCharShape(p: Paragraph) {
    p.createCharShape()
    val pcs: ParaCharShape = p.charShape
    // 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
    pcs.addParaCharShape(0, 1)
}

private fun setParaLineSeg(p: Paragraph) {
    p.createLineSeg()
    val pls: ParaLineSeg = p.getLineSeg()
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

private fun ptToLineHeight(pt: Double): Int {
    return (pt * 100.0f).toInt()
}

private fun mmToHwp(mm: Double): Long {
    return (mm * 72000.0f / 254.0f + 0.5f).toLong()
}