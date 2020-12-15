package me.dgahn.hwpdsl

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
import kr.dogfoot.hwplib.`object`.bodytext.control.table.DivideAtPageBoundary
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BackSlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderThickness
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.SlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.ImageFillType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PatternType
import kr.dogfoot.hwplib.`object`.docinfo.charshape.EmphasisSort
import kr.dogfoot.hwplib.`object`.docinfo.charshape.OutterLineSort
import kr.dogfoot.hwplib.`object`.docinfo.charshape.ShadowSort
import kr.dogfoot.hwplib.`object`.docinfo.charshape.UnderLineSort

data class CtrlHeaderStyle(
    val isLikeWord: Boolean = true,
    val isApplyLineSpace: Boolean = false,
    val vertRelTo: VertRelTo = VertRelTo.Para,
    val vertRelativeArrange: RelativeArrange = RelativeArrange.TopOrLeft,
    val horzRelTo: HorzRelTo = HorzRelTo.Para,
    val horzRelativeArrange: RelativeArrange = RelativeArrange.TopOrLeft,
    val isVertRelToParaLimit: Boolean = false,
    val isAllowOverlap: Boolean = false,
    val widthCriterion: WidthCriterion = WidthCriterion.Absolute,
    val heightCriterion: HeightCriterion = HeightCriterion.Absolute,
    val isProtectSize: Boolean = false,
    val textFlowMethod: TextFlowMethod = TextFlowMethod.Tight,
    val textHorzArrange: TextHorzArrange = TextHorzArrange.BothSides,
    val objectNumberSort: ObjectNumberSort = ObjectNumberSort.Table,
    val offsetX: Double = 0.0,
    val offsetY: Double = 0.0,
    val width: Double = 100.0,
    val height: Double = 60.0,
    val zOrder: Int = 1,
    val outterMarginLeft: Int = 10,
    val outterMarginRight: Int = 10,
    val outterMarginTop: Int = 100,
    val outterMarginBottom: Int = 100,
)

data class TableRecordStyle(
    val divideAtPageBoundary: DivideAtPageBoundary = DivideAtPageBoundary.DivideByCell,
    val isAutoRepeatTitleRow: Boolean = false,
    val cellSpacing: Int = 0,
    val leftInnerMargin: Int = 0,
    val rightInnerMargin: Int = 0,
    val topInnerMargin: Int = 0,
    val bottomInnerMargin: Int = 0
)

data class TableStyle(
    val ctrlHeaderStyle: CtrlHeaderStyle = CtrlHeaderStyle(),
    val tableRecordStyle: TableRecordStyle = TableRecordStyle(),
    val paragraphStyle: ParagraphStyle = ParagraphStyle()
)

data class BorderFillStyle(
    val is3DEffect: Boolean = false,
    val isShadowEffect: Boolean = false,
    val slashDiagonalShape: SlashDiagonalShape = SlashDiagonalShape.None,
    val backSlashDiagonalShape: BackSlashDiagonalShape = BackSlashDiagonalShape.None,
    val leftBorderType: BorderType = BorderType.None,
    val leftBorderThickness: BorderThickness = BorderThickness.MM0_5,
    val leftBorderColorValue: Long = 0x0,
    val rightBorderType: BorderType = BorderType.None,
    val rightBorderThickness: BorderThickness = BorderThickness.MM0_5,
    val rightBorderColorValue: Long = 0x0,
    val topBorderType: BorderType = BorderType.None,
    val topBorderThickness: BorderThickness = BorderThickness.MM0_5,
    val topBorderColorValue: Long = 0x0,
    val bottomBorderType: BorderType = BorderType.None,
    val bottomBorderThickness: BorderThickness = BorderThickness.MM0_5,
    val bottomBorderColorValue: Long = 0x0,
    val diagonalSort: BorderType = BorderType.None,
    val diagonalThickness: BorderThickness = BorderThickness.MM0_5,
    val diagonalColorValue: Long = 0x0,
    val isFillInfoTypePatternFill: Boolean = true,
)

data class PatternFillStyle(
    val patternType: PatternType = PatternType.None,
    // 0x00BBGGRR
    val backColorValue: Long = -1,
    val patternColorValue: Long = 0,
    val imageFillTypeFitSize: ImageFillType = ImageFillType.FitSize
)

data class ListHeaderStyle(
    val paraCount: Int = 1,
    val textDirection: TextDirection = TextDirection.Horizontal,
    val lineChange: LineChange = LineChange.Normal,
    val textVerticalAlignment: TextVerticalAlignment = TextVerticalAlignment.Center,
    val isProtectCell: Boolean = false,
    val isEditableAtFormMode: Boolean = false,
    val colSpan: Int = 1,
    val rowSpan: Int = 1,
    val width: Double = 50.0,
    val height: Double = 30.0,
    val leftMargin: Int = 0,
    val rightMargin: Int = 0,
    val topMargin: Int = 0,
    val bottomMargin: Int = 0,
    val textWidth: Double = 50.0,
    val fieldName: String = ""
)

data class TdStyle(
    val borderFileStyle: BorderFillStyle = BorderFillStyle(),
    val patternFillStyle: PatternFillStyle = PatternFillStyle(),
    val listHeaderStyle: ListHeaderStyle = ListHeaderStyle(),
    val paragraphStyle: ParagraphStyle = ParagraphStyle()
)

data class ParagraphStyle(
    val paragraphStartPos: Int = 0,
    val boldStartPos: Int = 0,
    val boldEndPos: Int = 0,
    val ratios: Short = 100.toShort(),
    val charSpaces: Byte = 0.toByte(),
    val relativeSizes: Short = 100.toShort(),
    val charOffsets: Byte = 0.toByte(),
    val baseSize: Int = ptToLineHeight(11.0),
    val isItalic: Boolean = false,
    val isBold: Boolean = false,
    val underLineSort: UnderLineSort = UnderLineSort.None,
    val outterLineSort: OutterLineSort = OutterLineSort.None,
    val shadowSort: ShadowSort = ShadowSort.None,
    val isEmboss: Boolean = false,
    val isEngrave: Boolean = false,
    val isSuperScript: Boolean = false,
    val isSubScript: Boolean = false,
    val isStrikeLine: Boolean = false,
    val emphasisSort: EmphasisSort = EmphasisSort.None,
    val isUsingSpaceAppropriateForFont: Boolean = false,
    val strikeLineShape: BorderType = BorderType.None,
    val isKerning: Boolean = false,
    val shadowGap1: Byte = 0.toByte(),
    val shadowGap2: Byte = 0.toByte(),
    val charColorValue: Long = 0x00000000,
    val underLineColorValue: Long = 0x00000000,
    val shadeColorValue: Long = -1,
    val shadowColorValue: Long = 0x00b2b2b2,
    val borderFillId: Int = 0,
    val fontName: String = "바탕",
    val hasBaseFont: Boolean = false,
    val hasFontInfo: Boolean = false,
    val hasSubstituteFont: Boolean = false
)
