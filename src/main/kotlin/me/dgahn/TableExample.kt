//package me.dgahn
//
//import kr.dogfoot.hwplib.`object`.HWPFile
//import kr.dogfoot.hwplib.`object`.bodytext.Section
//import kr.dogfoot.hwplib.`object`.bodytext.control.ControlTable
//import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HeightCriterion
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HorzRelTo
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.ObjectNumberSort
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.RelativeArrange
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextFlowMethod
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextHorzArrange
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.VertRelTo
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.WidthCriterion
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.textbox.LineChange
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.textbox.TextVerticalAlignment
//import kr.dogfoot.hwplib.`object`.bodytext.control.table.DivideAtPageBoundary
//import kr.dogfoot.hwplib.`object`.bodytext.control.table.ListHeaderForCell
//import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
//import kr.dogfoot.hwplib.`object`.bodytext.paragraph.charshape.ParaCharShape
//import kr.dogfoot.hwplib.`object`.bodytext.paragraph.header.ParaHeader
//import kr.dogfoot.hwplib.`object`.bodytext.paragraph.lineseg.ParaLineSeg
//import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText
//import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BackSlashDiagonalShape
//import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderThickness
//import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
//import kr.dogfoot.hwplib.`object`.docinfo.borderfill.SlashDiagonalShape
//import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PatternType
//import kr.dogfoot.hwplib.reader.HWPReader
//import kr.dogfoot.hwplib.writer.HWPWriter
//import java.io.UnsupportedEncodingException
//
//
//class Inserting_Table {
//    private var hwpFile: HWPFile? = null
//    private var table: ControlTable? = null
//    private var row: Row? = null
//    private var cell: Cell? = null
//    private var borderFillIDForCell = 0
//    private var zOrder = 0
//    private fun makeTable(hwpFile2: HWPFile) {
//        hwpFile = hwpFile2
//        createTableControlAtFirstParagraph()
//        setCtrlHeaderRecord()
//        setTableRecordFor2By2Cells()
//        add2By2Cell()
//    }
//
//    private fun createTableControlAtFirstParagraph() {
//        val firstSection: Section = hwpFile!!.bodyText.sectionList[0]
//        val firstParagraph: Paragraph = firstSection.getParagraph(0)
//
//        // 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
//        firstParagraph.getText().addExtendCharForTable()
//
//        // 문단에 표 컨트롤 추가한다.
//        table = firstParagraph.addNewControl(ControlType.Table) as ControlTable
//    }
//
//    private fun setCtrlHeaderRecord() {
//        val ctrlHeader = table!!.header
//        ctrlHeader.property.isLikeWord = false
//        ctrlHeader.property.isApplyLineSpace = false
//        ctrlHeader.property.vertRelTo = VertRelTo.Para
//        ctrlHeader.property.vertRelativeArrange = RelativeArrange.TopOrLeft
//        ctrlHeader.property.horzRelTo = HorzRelTo.Para
//        ctrlHeader.property.horzRelativeArrange = RelativeArrange.TopOrLeft
//        ctrlHeader.property.isVertRelToParaLimit = false
//        ctrlHeader.property.isAllowOverlap = false
//        ctrlHeader.property.widthCriterion = WidthCriterion.Absolute
//        ctrlHeader.property.heightCriterion = HeightCriterion.Absolute
//        ctrlHeader.property.isProtectSize = false
//        ctrlHeader.property.textFlowMethod = TextFlowMethod.Tight
//        ctrlHeader.property.textHorzArrange = TextHorzArrange.BothSides
//        ctrlHeader.property.objectNumberSort = ObjectNumberSort.Table
//        ctrlHeader.setxOffset(mmToHwp(20.0))
//        ctrlHeader.setyOffset(mmToHwp(20.0))
//        ctrlHeader.width = mmToHwp(100.0)
//        ctrlHeader.height = mmToHwp(60.0)
//        ctrlHeader.setzOrder(zOrder++)
//        ctrlHeader.outterMarginLeft = 0
//        ctrlHeader.outterMarginRight = 0
//        ctrlHeader.outterMarginTop = 0
//        ctrlHeader.outterMarginBottom = 0
//    }
//
//    private fun mmToHwp(mm: Double): Long {
//        return (mm * 72000.0f / 254.0f + 0.5f).toLong()
//    }
//
//    private fun setTableRecordFor2By2Cells() {
//        val tableRecord: Table = table!!.table
//        tableRecord.getProperty().setDivideAtPageBoundary(DivideAtPageBoundary.DivideByCell)
//        tableRecord.getProperty().setAutoRepeatTitleRow(false)
//        tableRecord.setRowCount(2)
//        tableRecord.setColumnCount(2)
//        tableRecord.setCellSpacing(0)
//        tableRecord.setLeftInnerMargin(0)
//        tableRecord.setRightInnerMargin(0)
//        tableRecord.setTopInnerMargin(0)
//        tableRecord.setBottomInnerMargin(0)
//        tableRecord.setBorderFillId(borderFillIDForTableOutterLine)
//        tableRecord.getCellCountOfRowList().add(2)
//        tableRecord.getCellCountOfRowList().add(2)
//    }
//
//    private val borderFillIDForTableOutterLine: Int
//        private get() {
//            val bf = hwpFile!!.docInfo.addNewBorderFill()
//            bf.property.is3DEffect = false
//            bf.property.isShadowEffect = false
//            bf.property.slashDiagonalShape = SlashDiagonalShape.None
//            bf.property.backSlashDiagonalShape = BackSlashDiagonalShape.None
//            bf.leftBorder.type = BorderType.None
//            bf.leftBorder.thickness = BorderThickness.MM0_5
//            bf.leftBorder.color.value = 0x0
//            bf.rightBorder.type = BorderType.None
//            bf.rightBorder.thickness = BorderThickness.MM0_5
//            bf.rightBorder.color.value = 0x0
//            bf.topBorder.type = BorderType.None
//            bf.topBorder.thickness = BorderThickness.MM0_5
//            bf.topBorder.color.value = 0x0
//            bf.bottomBorder.type = BorderType.None
//            bf.bottomBorder.thickness = BorderThickness.MM0_5
//            bf.bottomBorder.color.value = 0x0
//            bf.diagonalSort = BorderType.None
//            bf.diagonalThickness = BorderThickness.MM0_5
//            bf.diagonalColor.value = 0x0
//            bf.fillInfo.type.setPatternFill(true)
//            bf.fillInfo.createPatternFill()
//            val pf = bf.fillInfo.patternFill
//            pf.patternType = PatternType.None
//            pf.backColor.value = -1
//            pf.patternColor.value = 0
//            return hwpFile!!.docInfo.borderFillList.size
//        }
//
//    private fun add2By2Cell() {
//        borderFillIDForCell = getBorderFillIDForCell()
//        addFirstRow()
//        addSecondRow()
//    }
//
//    private fun getBorderFillIDForCell(): Int {
//        val bf = hwpFile!!.docInfo.addNewBorderFill()
//        bf.property.is3DEffect = false
//        bf.property.isShadowEffect = false
//        bf.property.slashDiagonalShape = SlashDiagonalShape.None
//        bf.property.backSlashDiagonalShape = BackSlashDiagonalShape.None
//        bf.leftBorder.type = BorderType.Solid
//        bf.leftBorder.thickness = BorderThickness.MM0_5
//        bf.leftBorder.color.value = 0x0
//        bf.rightBorder.type = BorderType.Solid
//        bf.rightBorder.thickness = BorderThickness.MM0_5
//        bf.rightBorder.color.value = 0x0
//        bf.topBorder.type = BorderType.Solid
//        bf.topBorder.thickness = BorderThickness.MM0_5
//        bf.topBorder.color.value = 0x0
//        bf.bottomBorder.type = BorderType.Solid
//        bf.bottomBorder.thickness = BorderThickness.MM0_5
//        bf.bottomBorder.color.value = 0x0
//        bf.diagonalSort = BorderType.None
//        bf.diagonalThickness = BorderThickness.MM0_5
//        bf.diagonalColor.value = 0x0
//        bf.fillInfo.type.setPatternFill(true)
//        bf.fillInfo.createPatternFill()
//        val pf = bf.fillInfo.patternFill
//        pf.patternType = PatternType.None
//        pf.backColor.value = -1
//        pf.patternColor.value = 0
//        return hwpFile!!.docInfo.borderFillList.size
//    }
//
//    private fun addFirstRow() {
//        row = table!!.addNewRow()
//        addLeftTopCell()
//        addRightTopCell()
//    }
//
//    private fun addLeftTopCell() {
//        cell = row.addNewCell()
//        setListHeaderForCell(0, 0)
//        setParagraphForCell("왼쪽 위 셀")
//    }
//
//    private fun setListHeaderForCell(colIndex: Int, rowIndex: Int) {
//        val lh: ListHeaderForCell = cell.getListHeader()
//        lh.paraCount = 1
//        lh.property.textDirection = TextDirection.Horizontal
//        lh.property.lineChange = LineChange.Normal
//        lh.property.textVerticalAlignment = TextVerticalAlignment.Center
//        lh.property.isProtectCell = false
//        lh.property.isEditableAtFormMode = false
//        lh.colIndex = colIndex
//        lh.rowIndex = rowIndex
//        lh.colSpan = 1
//        lh.rowSpan = 1
//        lh.width = mmToHwp(50.0)
//        lh.height = mmToHwp(30.0)
//        lh.leftMargin = 0
//        lh.rightMargin = 0
//        lh.topMargin = 0
//        lh.bottomMargin = 0
//        lh.borderFillId = borderFillIDForCell
//        lh.textWidth = mmToHwp(50.0)
//        lh.fieldName = ""
//    }
//
//    private fun setParagraphForCell(text: String) {
//        val p: Paragraph = cell.getParagraphList().addNewParagraph()
//        setParaHeader(p)
//        setParaText(p, text)
//        setParaCharShape(p)
//        setParaLineSeg(p)
//    }
//
//    private fun setParaHeader(p: Paragraph) {
//        val ph: ParaHeader = p.getHeader()
//        ph.isLastInList = true
//        // 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
//        ph.paraShapeId = 1
//        // 셀의 스타일을이미 만들어진 스타일으로 사용함
//        ph.styleId = 1.toShort()
//        ph.divideSort.isDivideSection = false
//        ph.divideSort.isDivideMultiColumn = false
//        ph.divideSort.isDividePage = false
//        ph.divideSort.isDivideColumn = false
//        ph.charShapeCount = 1
//        ph.rangeTagCount = 0
//        ph.lineAlignCount = 1
//        ph.instanceID = 0
//        ph.isMergedByTrack = 0
//    }
//
//    private fun setParaText(p: Paragraph, text2: String) {
//        p.createText()
//        val pt: ParaText = p.getText()
//        try {
//            pt.addString(text2)
//        } catch (e: UnsupportedEncodingException) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//        }
//    }
//
//    private fun setParaCharShape(p: Paragraph) {
//        p.createCharShape()
//        val pcs: ParaCharShape = p.getCharShape()
//        // 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
//        pcs.addParaCharShape(0, 1)
//    }
//
//    private fun setParaLineSeg(p: Paragraph) {
//        p.createLineSeg()
//        val pls: ParaLineSeg = p.getLineSeg()
//        val lsi = pls.addNewLineSegItem()
//        lsi.textStartPositon = 0
//        lsi.lineVerticalPosition = 0
//        lsi.lineHeight = ptToLineHeight(10.0)
//        lsi.textPartHeight = ptToLineHeight(10.0)
//        lsi.distanceBaseLineToLineVerticalPosition = ptToLineHeight(10.0 * 0.85)
//        lsi.lineSpace = ptToLineHeight(3.0)
//        lsi.startPositionFromColumn = 0
//        lsi.segmentWidth = mmToHwp(50.0).toInt()
//        lsi.tag.firstSegmentAtLine = true
//        lsi.tag.lastSegmentAtLine = true
//    }
//
//    private fun ptToLineHeight(pt: Double): Int {
//        return (pt * 100.0f).toInt()
//    }
//
//    private fun addRightTopCell() {
//        cell = row.addNewCell()
//        setListHeaderForCell(1, 0)
//        setParagraphForCell("오른쪽  위 셀")
//    }
//
//    private fun addSecondRow() {
//        row = table!!.addNewRow()
//        addLeftBottomCell()
//        addRightBottomCell()
//    }
//
//    private fun addLeftBottomCell() {
//        cell = row.addNewCell()
//        setListHeaderForCell(0, 1)
//        setParagraphForCell("왼쪽 아래 셀")
//    }
//
//    private fun addRightBottomCell() {
//        cell = row.addNewCell()
//        setListHeaderForCell(1, 1)
//        setParagraphForCell("오른쪽 아래 셀")
//    }
//
//    companion object {
//        @Throws(Exception::class)
//        @JvmStatic
//        fun main(args: Array<String>) {
//            val filename = "sample_hwp" + File.separator.toString() + "test-blank.hwp"
//            val hwpFile = HWPReader.fromFile(filename)
//            if (hwpFile != null) {
//                val tmt = Inserting_Table()
//                tmt.makeTable(hwpFile)
//                val writePath = "sample_hwp" + File.separator.toString() + "test-making-table.hwp"
//                HWPWriter.toFile(hwpFile, writePath)
//            }
//        }
//    }
//}