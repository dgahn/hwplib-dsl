package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.Section
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlTable
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.bodytext.control.table.Cell
import kr.dogfoot.hwplib.`object`.bodytext.control.table.ListHeaderForCell
import kr.dogfoot.hwplib.`object`.bodytext.control.table.Row
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.docinfo.BorderFill
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
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
    tableStyle: TableStyle = TableStyle(),
    block: TABLE.() -> Unit = {}
) {
    val builder = TableBuilder(
        hwpFile = consumer.hwpFile,
        rowSize = rowSize,
        colSize = colSize,
        section = consumer.currentSection,
        ctrlHeaderStyle = tableStyle.ctrlHeaderStyle,
        tableRecordStyle = tableStyle.tableRecordStyle
    )
    TABLE(consumer = consumer, builder = builder).visit(block)
}

fun SECTION.table(
    rowSize: Int,
    colSize: Int,
    tableStyle: TableStyle = TableStyle(),
    block: TABLE.() -> Unit = {}
) {
    val builder = TableBuilder(
        hwpFile = consumer.hwpFile,
        rowSize = rowSize,
        colSize = colSize,
        section = consumer.currentSection,
        ctrlHeaderStyle = tableStyle.ctrlHeaderStyle,
        tableRecordStyle = tableStyle.tableRecordStyle
    )
    TABLE(consumer = consumer, builder = builder).visit(block)
}

class TableBuilder(
    override val hwpFile: HWPFile,
    val section: Section,
    val rowSize: Int,
    val colSize: Int,
    var ctrlHeaderStyle: CtrlHeaderStyle,
    var tableRecordStyle: TableRecordStyle
) : HwpTagBuilder {
    lateinit var control: ControlTable

    override fun build() {
        val paragraph = runCatching { section.getParagraph(0) }.getOrElse {
            section.addNewParagraph().apply {
                setParaHeader(this)
                setParaText(this, "")
                setParaCharShape(this)
                setParaLineSeg(this)
            }
        }

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
        controlTable: ControlTable
    ) {
        val ctrlHeader = controlTable.header
        ctrlHeader.property.isLikeWord = ctrlHeaderStyle.isLikeWord
        ctrlHeader.property.isApplyLineSpace = ctrlHeaderStyle.isApplyLineSpace
        ctrlHeader.property.vertRelTo = ctrlHeaderStyle.vertRelTo
        ctrlHeader.property.vertRelativeArrange = ctrlHeaderStyle.vertRelativeArrange
        ctrlHeader.property.horzRelTo = ctrlHeaderStyle.horzRelTo
        ctrlHeader.property.horzRelativeArrange = ctrlHeaderStyle.horzRelativeArrange
        ctrlHeader.property.isVertRelToParaLimit = ctrlHeaderStyle.isVertRelToParaLimit
        ctrlHeader.property.isAllowOverlap = ctrlHeaderStyle.isAllowOverlap
        ctrlHeader.property.widthCriterion = ctrlHeaderStyle.widthCriterion
        ctrlHeader.property.heightCriterion = ctrlHeaderStyle.heightCriterion
        ctrlHeader.property.isProtectSize = ctrlHeaderStyle.isProtectSize
        ctrlHeader.property.textFlowMethod = ctrlHeaderStyle.textFlowMethod
        ctrlHeader.property.textHorzArrange = ctrlHeaderStyle.textHorzArrange
        ctrlHeader.property.objectNumberSort = ctrlHeaderStyle.objectNumberSort
        ctrlHeader.setxOffset(mmToHwp(ctrlHeaderStyle.offsetX))
        ctrlHeader.setyOffset(mmToHwp(ctrlHeaderStyle.offsetY))
        ctrlHeader.width = mmToHwp(ctrlHeaderStyle.width)
        ctrlHeader.height = mmToHwp(ctrlHeaderStyle.height)
        ctrlHeader.setzOrder(ctrlHeaderStyle.zOrder)
        ctrlHeader.outterMarginLeft = ctrlHeaderStyle.outterMarginLeft
        ctrlHeader.outterMarginRight = ctrlHeaderStyle.outterMarginRight
        ctrlHeader.outterMarginTop = ctrlHeaderStyle.outterMarginTop
        ctrlHeader.outterMarginBottom = ctrlHeaderStyle.outterMarginBottom

        val tableRecord = controlTable.table
        tableRecord.property.divideAtPageBoundary = tableRecordStyle.divideAtPageBoundary
        tableRecord.property.isAutoRepeatTitleRow = tableRecordStyle.isAutoRepeatTitleRow
        tableRecord.cellSpacing = tableRecordStyle.cellSpacing
        tableRecord.leftInnerMargin = tableRecordStyle.leftInnerMargin
        tableRecord.rightInnerMargin = tableRecordStyle.rightInnerMargin
        tableRecord.topInnerMargin = tableRecordStyle.topInnerMargin
        tableRecord.bottomInnerMargin = tableRecordStyle.bottomInnerMargin
        tableRecord.rowCount = rowCount
        tableRecord.columnCount = colCount
        tableRecord.cellCountOfRowList.add(rowCount)
        tableRecord.cellCountOfRowList.add(colCount)
        tableRecord.borderFillId = getBorderFillIDForTableOutterLine(hwpFile)
    }

    private fun getBorderFillIDForTableOutterLine(
        hwpFile: HWPFile,
        borderFillStyle: BorderFillStyle = BorderFillStyle(),
        patternFillStyle: PatternFillStyle = PatternFillStyle()
    ): Int {
        val bf = hwpFile.docInfo.addNewBorderFill()
        bf.property.is3DEffect = borderFillStyle.is3DEffect
        bf.property.isShadowEffect = borderFillStyle.isShadowEffect
        bf.property.slashDiagonalShape = borderFillStyle.slashDiagonalShape
        bf.property.backSlashDiagonalShape = borderFillStyle.backSlashDiagonalShape
        bf.leftBorder.type = borderFillStyle.leftBorderType
        bf.leftBorder.thickness = borderFillStyle.leftBorderThickness
        bf.leftBorder.color.value = borderFillStyle.leftBorderColorValue
        bf.rightBorder.type = borderFillStyle.rightBorderType
        bf.rightBorder.thickness = borderFillStyle.rightBorderThickness
        bf.rightBorder.color.value = borderFillStyle.rightBorderColorValue
        bf.topBorder.type = borderFillStyle.topBorderType
        bf.topBorder.thickness = borderFillStyle.topBorderThickness
        bf.topBorder.color.value = borderFillStyle.topBorderColorValue
        bf.bottomBorder.type = borderFillStyle.bottomBorderType
        bf.bottomBorder.thickness = borderFillStyle.bottomBorderThickness
        bf.bottomBorder.color.value = borderFillStyle.bottomBorderColorValue
        bf.diagonalSort = borderFillStyle.diagonalSort
        bf.diagonalThickness = borderFillStyle.diagonalThickness
        bf.diagonalColor.value = borderFillStyle.diagonalColorValue
        bf.fillInfo.type.setPatternFill(borderFillStyle.isFillInfoTypePatternFill)
        bf.fillInfo.createPatternFill()
        val pf = bf.fillInfo.patternFill
        pf.patternType = patternFillStyle.patternType
        pf.backColor.value = patternFillStyle.backColorValue
        pf.patternColor.value = patternFillStyle.patternColorValue
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

fun TR.td(
    tdStyle: TdStyle = TdStyle(
        borderFileStyle = BorderFillStyle(
            leftBorderType = BorderType.Solid,
            rightBorderType = BorderType.Solid,
            topBorderType = BorderType.Solid,
            bottomBorderType = BorderType.Solid,
        )
    ),
    block: TD.() -> Unit = {}
) {
    val builder = TdBuilder(
        hwpFile = consumer.hwpFile,
        rowTag = row,
        row = position,
        col = countCurrentCol(),
        borderFillStyle = tdStyle.borderFileStyle,
        patternFillStyle = tdStyle.patternFillStyle,
        listHeaderStyle = tdStyle.listHeaderStyle
    )
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
    val col: Int,
    var borderFillStyle: BorderFillStyle = BorderFillStyle(
        leftBorderType = BorderType.Solid,
        rightBorderType = BorderType.Solid,
        topBorderType = BorderType.Solid,
        bottomBorderType = BorderType.Solid,
    ),
    var patternFillStyle: PatternFillStyle = PatternFillStyle(),
    var listHeaderStyle: ListHeaderStyle = ListHeaderStyle()
) : HwpTagBuilder {

    lateinit var cell: Cell
    lateinit var bf: BorderFill

    override fun build() {
        val borderFillIDForCell = getBorderFillIDForCell(hwpFile = hwpFile)
        val cell = this.rowTag.addNewCell()
        setListHeaderForCell(col, row, cell, borderFillIDForCell)
        this.cell = cell
    }

    override fun completed() = Unit

    private fun getBorderFillIDForCell(
        hwpFile: HWPFile
    ): Int {
        bf = hwpFile.docInfo.addNewBorderFill()
        bf.property.is3DEffect = borderFillStyle.is3DEffect
        bf.property.isShadowEffect = borderFillStyle.isShadowEffect
        bf.property.slashDiagonalShape = borderFillStyle.slashDiagonalShape
        bf.property.backSlashDiagonalShape = borderFillStyle.backSlashDiagonalShape
        bf.leftBorder.type = borderFillStyle.leftBorderType
        bf.leftBorder.thickness = borderFillStyle.leftBorderThickness
        bf.leftBorder.color.value = borderFillStyle.leftBorderColorValue
        bf.rightBorder.type = borderFillStyle.rightBorderType
        bf.rightBorder.thickness = borderFillStyle.rightBorderThickness
        bf.rightBorder.color.value = borderFillStyle.rightBorderColorValue
        bf.topBorder.type = borderFillStyle.topBorderType
        bf.topBorder.thickness = borderFillStyle.topBorderThickness
        bf.topBorder.color.value = borderFillStyle.topBorderColorValue
        bf.bottomBorder.type = borderFillStyle.bottomBorderType
        bf.bottomBorder.thickness = borderFillStyle.bottomBorderThickness
        bf.bottomBorder.color.value = borderFillStyle.bottomBorderColorValue
        bf.diagonalSort = borderFillStyle.diagonalSort
        bf.diagonalThickness = borderFillStyle.diagonalThickness
        bf.diagonalColor.value = borderFillStyle.diagonalColorValue
        bf.fillInfo.type.setPatternFill(borderFillStyle.isFillInfoTypePatternFill)
        bf.fillInfo.createPatternFill()
        val pf = bf.fillInfo.patternFill
        pf.patternType = patternFillStyle.patternType
        pf.backColor.value = patternFillStyle.backColorValue
        pf.patternColor.value = patternFillStyle.patternColorValue
        return hwpFile.docInfo.borderFillList.size
    }

    fun setImageFill(
        binDataID: Int,
        patternFillStyle: PatternFillStyle = PatternFillStyle()
    ) {
        bf.fillInfo.type.setImageFill(true)
        bf.fillInfo.createImageFill()
        val imgF = bf.fillInfo.imageFill
        imgF.imageFillType = patternFillStyle.imageFillTypeFitSize
        imgF.pictureInfo.binItemID = binDataID
    }

    private fun setListHeaderForCell(
        colIndex: Int,
        rowIndex: Int,
        cell: Cell,
        borderFillIDForCell: Int
    ) {
        val lh: ListHeaderForCell = cell.listHeader
        lh.paraCount = listHeaderStyle.paraCount
        lh.property.textDirection = listHeaderStyle.textDirection
        lh.property.lineChange = listHeaderStyle.lineChange
        lh.property.textVerticalAlignment = listHeaderStyle.textVerticalAlignment
        lh.property.isProtectCell = listHeaderStyle.isProtectCell
        lh.property.isEditableAtFormMode = listHeaderStyle.isEditableAtFormMode
        lh.colIndex = colIndex
        lh.rowIndex = rowIndex
        lh.colSpan = listHeaderStyle.colSpan
        lh.rowSpan = listHeaderStyle.rowSpan
        lh.width = mmToHwp(listHeaderStyle.width)
        lh.height = mmToHwp(listHeaderStyle.height)
        lh.leftMargin = listHeaderStyle.leftMargin
        lh.rightMargin = listHeaderStyle.rightMargin
        lh.topMargin = listHeaderStyle.topMargin
        lh.bottomMargin = listHeaderStyle.bottomMargin
        lh.borderFillId = borderFillIDForCell
        lh.textWidth = mmToHwp(listHeaderStyle.textWidth)
        lh.fieldName = listHeaderStyle.fieldName
    }

    fun setParagraphForCell(text: String, cell: Cell) {
        val p: Paragraph = cell.paragraphList.addNewParagraph()
        setParaHeader(p)
        setParaText(p, text)
        setParaCharShape(p)
        setParaLineSeg(p)
    }
}
