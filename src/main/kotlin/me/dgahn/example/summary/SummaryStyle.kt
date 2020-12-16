package me.dgahn.example.summary

import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
import kr.dogfoot.hwplib.`object`.docinfo.parashape.Alignment
import me.dgahn.hwpdsl.BorderFillStyle
import me.dgahn.hwpdsl.CtrlHeaderStyle
import me.dgahn.hwpdsl.ListHeaderStyle
import me.dgahn.hwpdsl.ParagraphStyle
import me.dgahn.hwpdsl.PatternFillStyle
import me.dgahn.hwpdsl.TableRecordStyle
import me.dgahn.hwpdsl.TableStyle
import me.dgahn.hwpdsl.TdStyle

private const val pageWidth = 200.0
private const val tableWidth = pageWidth
private const val contentTdHeight = 10.0
private const val thBackColorValue: Long = 0x00EFE7DB
private const val tdBackColorValue: Long = 0x00ECFEFF
private const val borderColorValue: Long = 0x00D9D6D1
private const val firstTdWidth = 10.0
private const val secondTdWidth = 30.0
private const val thirdTdWidth = 40.0
private const val forthTdWidth = 15.0
private const val fifthTdWidth = 20.0
private const val sixthTdWidth =
    tableWidth - firstTdWidth - secondTdWidth - thirdTdWidth - forthTdWidth - fifthTdWidth
private const val fontSize = 12.0

internal const val thTableHeight = contentTdHeight

internal val titleNameTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = tableWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal val thFirstTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(
        topBorderType = BorderType.Solid,
        topBorderColorValue = borderColorValue,
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = thBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = firstTdWidth,
        height = contentTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize,
        isBold = true
    )
)

internal val thSecondTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(
        topBorderType = BorderType.Solid,
        topBorderColorValue = borderColorValue,
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = thBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = secondTdWidth,
        height = contentTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize,
        isBold = true
    )
)

internal val thThirdTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(
        topBorderType = BorderType.Solid,
        topBorderColorValue = borderColorValue,
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = thBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = thirdTdWidth,
        height = contentTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize,
        isBold = true
    )
)

internal val thForthTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(
        topBorderType = BorderType.Solid,
        topBorderColorValue = borderColorValue,
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = thBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = forthTdWidth,
        height = contentTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize,
        isBold = true
    )
)

internal val thFifthTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(
        topBorderType = BorderType.Solid,
        topBorderColorValue = borderColorValue,
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = thBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = fifthTdWidth,
        height = contentTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize,
        isBold = true
    )
)

internal val thSixthTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(
        topBorderType = BorderType.Solid,
        topBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = thBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = sixthTdWidth,
        height = contentTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize,
        isBold = true
    )
)

internal val firstThTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = firstTdWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal fun getFirstThTableStyle(mergeSize: Int = 1) = firstThTableStyle.copy(
    ctrlHeaderStyle = firstThTableStyle.ctrlHeaderStyle.copy(height = thTableHeight * mergeSize)
)

internal fun getFirstTdTableStyle(mergeSize: Int = 1) = thFirstTdStyle.copy(
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = tdBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = firstTdWidth,
        height = thTableHeight * mergeSize
    ),
    borderFileStyle = BorderFillStyle(
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    )
)

internal val secondThTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = secondTdWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal fun getSecondThTableStyle(mergeSize: Int = 1) = secondThTableStyle.copy(
    ctrlHeaderStyle = secondThTableStyle.ctrlHeaderStyle.copy(
        height = thTableHeight * mergeSize
    )
)

internal fun getSecondTdTableStyle(mergeSize: Int = 1) = thSecondTdStyle.copy(
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = tdBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = secondTdWidth,
        height = thTableHeight * mergeSize
    ),
    borderFileStyle = BorderFillStyle(
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    )
)

internal val thirdTdTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = thirdTdWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal val thirdTdStyle = thThirdTdStyle.copy(
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = tdBackColorValue
    ),
    borderFileStyle = BorderFillStyle(
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    )
)

internal val forthThTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = forthTdWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal val forthTdStyle = thForthTdStyle.copy(
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = tdBackColorValue
    ),
    borderFileStyle = BorderFillStyle(
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    )
)

internal val fifthThTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = fifthTdWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal val fifthTdStyle = thFifthTdStyle.copy(
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = tdBackColorValue
    ),
    borderFileStyle = BorderFillStyle(
        rightBorderType = BorderType.Solid,
        rightBorderColorValue = borderColorValue,
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    )
)

internal val sixthThTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = sixthTdWidth,
        height = thTableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal fun getSixThTableStyle(mergeSize: Int = 1) = sixthThTableStyle.copy(
    ctrlHeaderStyle = secondThTableStyle.ctrlHeaderStyle.copy(
        height = thTableHeight * mergeSize
    )
)

internal fun getSixThTdTableStyle(mergeSize: Int = 1) = thSixthTdStyle.copy(
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center,
        baseSize = fontSize
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = tdBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = sixthTdWidth - 0.1,
        height = thTableHeight * mergeSize
    ),
    borderFileStyle = BorderFillStyle(
        bottomBorderType = BorderType.Solid,
        bottomBorderColorValue = borderColorValue,
    )
)
