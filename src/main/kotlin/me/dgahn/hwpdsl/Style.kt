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
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineArrowShape
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineArrowSize
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineEndShape
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineType
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.OutlineStyle
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.shadowinfo.ShadowType
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.textbox.LineChange
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.textbox.TextVerticalAlignment
import kr.dogfoot.hwplib.`object`.bodytext.control.table.DivideAtPageBoundary
import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataCompress
import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataState
import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BackSlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderThickness
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.SlashDiagonalShape
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.ImageFillType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PatternType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PictureEffect
import kr.dogfoot.hwplib.`object`.docinfo.charshape.EmphasisSort
import kr.dogfoot.hwplib.`object`.docinfo.charshape.OutterLineSort
import kr.dogfoot.hwplib.`object`.docinfo.charshape.ShadowSort
import kr.dogfoot.hwplib.`object`.docinfo.charshape.UnderLineSort
import kr.dogfoot.hwplib.`object`.docinfo.parashape.Alignment
import kotlin.random.Random

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
    val outterMarginLeft: Int = 0,
    val outterMarginRight: Int = 0,
    val outterMarginTop: Int = 0,
    val outterMarginBottom: Int = 0,
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
    val ratios: Short = 100.toShort(),
    val charSpaces: Byte = 0.toByte(),
    val relativeSizes: Short = 100.toShort(),
    val charOffsets: Byte = 0.toByte(),
    val baseSize: Double = 11.0,
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
    val hasSubstituteFont: Boolean = false,
    val paragraphAlignment: Alignment = Alignment.Left,
    val lineSpace: Int = 100,
    val leftMargin: Double = 0.0,
    val rightMargin: Double = 0.0
) {
    val lineSpace2: Long
        get() = lineSpace.toLong()
}

data class BinDataStyle(
    val type: BinDataType = BinDataType.Embedding,
    val compress: BinDataCompress = BinDataCompress.ByStroageDefault,
    val state: BinDataState = BinDataState.NotAcceess,
    val format: String = "png",
)

data class CtrlHeaderGsoStyle(
    val isLikeWord: Boolean = true,
    val isApplyLineSpace: Boolean = false,
    val vertRelTo: VertRelTo = VertRelTo.Para,
    val vertRelativeArrange: RelativeArrange = RelativeArrange.TopOrLeft,
    val horzRelTo: HorzRelTo = HorzRelTo.Para,
    val horzRelativeArrange: RelativeArrange = RelativeArrange.TopOrLeft,
    val isVertRelToParaLimit: Boolean = true,
    val isAllowOverlap: Boolean = true,
    val widthCriterion: WidthCriterion = WidthCriterion.Absolute,
    val heightCriterion: HeightCriterion = HeightCriterion.Absolute,
    val isProtectSize: Boolean = false,
    val textFlowMethod: TextFlowMethod = TextFlowMethod.TopAndBottom,
    val textHorzArrange: TextHorzArrange = TextHorzArrange.BothSides,
    val objectNumberSort: ObjectNumberSort = ObjectNumberSort.Figure,
    val zOrder: Int = 0,
    val outterMarginLeft: Int = 0,
    val outterMarginRight: Int = 0,
    val outterMarginTop: Int = 0,
    val outterMarginBottom: Int = 0,
    val instanceId: Long = Random.nextLong(),
    val isPreventPageDivide: Boolean = false,
    val explanation: String? = null
)

data class LineInfoStyle(
    val lineEndShape: LineEndShape = LineEndShape.Flat,
    val startArrowShape: LineArrowShape = LineArrowShape.None,
    val startArrowSize: LineArrowSize = LineArrowSize.MiddleMiddle,
    val endArrowShape: LineArrowShape = LineArrowShape.None,
    val endArrowSize: LineArrowSize = LineArrowSize.MiddleMiddle,
    val isFillStartArrow: Boolean = true,
    val isFillEndArrow: Boolean = true,
    val lineType: LineType = LineType.None,
    val outlineStyle: OutlineStyle = OutlineStyle.Normal,
    val thickness: Int = 0,
    val colorValue: Long = 0,
)

data class ImageFillStyle(
    val imageFillType: ImageFillType = ImageFillType.FitSize,
    val brightness: Byte = 0.toByte(),
    val contrast: Byte = 0.toByte(),
    val effect: PictureEffect = PictureEffect.RealPicture,
)

data class ShadowInfoStyle(
    val type: ShadowType = ShadowType.None,
    val colorValue: Long = 0xc4c4c4,
    val offsetX: Int = 283,
    val offsetY: Int = 283,
    val transparnet: Short = 0.toShort()
)

data class ShapeComponentStyle(
    val offsetX: Int = 0,
    val offsetY: Int = 0,
    val groupingCount: Int = 0,
    val localFileVersion: Int = 1,
    val rotateAngle: Int = 0
)

data class ShapeComponentRectangleStyle(
    val roundRate: Byte = 0.toByte(),
    val x1: Int = 0,
    val y1: Int = 0,
    val x2: Int = 0,
    val y2: Int = 0,
    val x3: Int = 0,
    val y3: Int = 0,
    val x4: Int = 0,
    val y4: Int = 0,
)

data class ImgStyle(
    val binDataStyle: BinDataStyle = BinDataStyle(),
    val ctrlHeaderGsoStyle: CtrlHeaderGsoStyle = CtrlHeaderGsoStyle(),
    val shapeComponentStyle: ShapeComponentStyle = ShapeComponentStyle(),
    val lineInfoStyle: LineInfoStyle = LineInfoStyle(),
    val imgFillStyle: ImageFillStyle = ImageFillStyle(),
    val shadowInfoStyle: ShadowInfoStyle = ShadowInfoStyle(),
    val shapeComponentRectangleStyle: ShapeComponentRectangleStyle = ShapeComponentRectangleStyle(),
    val paragraphStyle: ParagraphStyle = ParagraphStyle()
)

data class PaperStyle(
    val leftMargin: Double = 5.0,
    val rightMargin: Double = 5.0,
    val topMargin: Double = 5.0,
    val bottomMargin: Double = 5.0,
    val headerMargin: Double = 0.0,
    val footerMargin: Double = 0.0,
    val gutterMargin: Double = 0.0,
    val footNoteShapeDivideLineSort: BorderType = BorderType.None,
    val footNoteShapeDivideLineThickness: BorderThickness = BorderThickness.MM0_5,
    val endNoteShapeDivideLineSort: BorderType = BorderType.None,
    val endNoteShapeDivideLineThickness: BorderThickness = BorderThickness.MM0_5,
)