package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.Section
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
import kr.dogfoot.hwplib.`object`.docinfo.BorderFill
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BackSlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderThickness
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.SlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.ImageFillType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PatternType
import java.awt.image.BufferedImage
import java.lang.RuntimeException

open class TABLE(
    override val consumer: HwpTagConsumer<*>,
    override val builder: TableBuilder,
    var currentRow: Int = 0
) : Tag {
    lateinit var control: ControlTable

    fun initControl(control: ControlTable) {
        this.control = control
    }

    fun countCurrentRow() = if (currentRow == builder.rowSize) {
        throw RuntimeException("No more rows can be added. max row size : ${builder.rowSize}")
    } else {
        currentRow++
    }
}

fun BODY.table(
    rowSize: Int,
    colSize: Int,
    block: TABLE.() -> Unit = {}
) {
    val builder = TableBuilder(
        hwpFile = consumer.hwpFile,
        rowSize = rowSize,
        colSize = colSize,
        section = consumer.currentSection
    )
    TABLE(consumer = consumer, builder = builder).visit(block)
}

class TableBuilder(
    override val hwpFile: HWPFile,
    val section: Section,
    val rowSize: Int,
    val colSize: Int
) : HwpTagBuilder {
    lateinit var control: ControlTable

    override fun build() {
        val paragraph = section.getParagraph(0)

        paragraph.text.addExtendCharForTable()
        paragraph.addNewControl(ControlType.Table).apply {
            control = this as ControlTable
            style(rowSize, colSize, hwpFile, control)
        }
    }

    override fun completed() = Unit

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
}

open class TR(
    override val consumer: HwpTagConsumer<*>,
    override val builder: TrBuilder,
    val row: Row,
    val position: Int,
    val colSize: Int,
    var currentCol: Int = 0
) : Tag {
    fun countCurrentCol() = if (currentCol == colSize) {
        throw RuntimeException("No more cols can be added. max col size : $colSize")
    } else {
        currentCol++
    }
}

fun TABLE.tr(block: TR.() -> Unit = {}) =
    TR(
        consumer = consumer,
        builder = TrBuilder(consumer.hwpFile),
        row = control.addNewRow(),
        position = countCurrentRow(),
        builder.colSize
    ).visit(block)

class TrBuilder(override val hwpFile: HWPFile) : HwpTagBuilder {
    override fun build() = Unit
    override fun completed() = Unit
}

open class TD(
    override val consumer: HwpTagConsumer<*>,
    override val builder: TdBuilder
) : Tag {
    lateinit var cell: Cell

    fun initCell(cell: Cell) {
        this.cell = cell
    }

    override operator fun String.unaryPlus() {
        text(this)
    }

    override fun text(s: String) {
        builder.setParagraphForCell(s, cell)
    }
}

fun TR.td(block: TD.() -> Unit = {}) {
    val builder = TdBuilder(consumer.hwpFile, row, position, countCurrentCol())
    TD(consumer = consumer, builder = builder).visit(block)
}

fun TD.img(src: BufferedImage, width: Int, height: Int, block: IMG.() -> Unit = {}) {
    val builder =
        ImgBuilder(
            hwpFile = consumer.hwpFile,
            width = width,
            src = src,
            height = height,
            tdBuilder = this.builder,
            section = consumer.currentSection
        )
    IMG(consumer = consumer, builder = builder).visit(block)
}

class TdBuilder(
    override val hwpFile: HWPFile,
    val rowTag: Row,
    val row: Int,
    val col: Int
) : HwpTagBuilder {

    lateinit var cell: Cell
    lateinit var bf: BorderFill

    override fun build() {
        val borderFillIDForCell = getBorderFillIDForCell(hwpFile)
        val cell = this.rowTag.addNewCell()
        setListHeaderForCell(col, row, cell, borderFillIDForCell)
        this.cell = cell
    }

    override fun completed() = Unit

    private fun getBorderFillIDForCell(hwpFile: HWPFile): Int {
        bf = hwpFile.docInfo.addNewBorderFill()
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

    fun setImageFill(binDataID: Int) {
        bf.fillInfo.type.setImageFill(true)
        bf.fillInfo.createImageFill()
        val imgF = bf.fillInfo.imageFill
        imgF.imageFillType = ImageFillType.FitSize
        imgF.pictureInfo.binItemID = binDataID
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

    fun setParagraphForCell(text: String, cell: Cell) {
        val p: Paragraph = cell.paragraphList.addNewParagraph()
        setParaHeader(p)
        setParaText(p, text)
        setParaCharShape(p)
        setParaLineSeg(p)
    }
}
