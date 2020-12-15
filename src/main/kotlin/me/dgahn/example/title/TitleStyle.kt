package me.dgahn.example.title

import me.dgahn.hwpdsl.BorderFillStyle
import me.dgahn.hwpdsl.CtrlHeaderStyle
import me.dgahn.hwpdsl.ListHeaderStyle
import me.dgahn.hwpdsl.ParagraphStyle
import me.dgahn.hwpdsl.PatternFillStyle
import me.dgahn.hwpdsl.TableRecordStyle
import me.dgahn.hwpdsl.TableStyle
import me.dgahn.hwpdsl.TdStyle

private const val titleNameWidth = 130.0
private const val timeWidth = 70.0
private const val tableWidth = titleNameWidth + timeWidth
private const val backColorValue: Long = 0x005c4719
private const val charColorValue: Long = 0x00FFFFFF
private const val tdHeight: Double = 15.0
private const val tableHeight: Double = tdHeight * 2
private const val titleFontSize: Double = 30.0

internal val titleNameTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = tableWidth,
        height = tableHeight
    ),
    tableRecordStyle = TableRecordStyle()
)

internal val titleNameTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(),
    patternFillStyle = PatternFillStyle(
        backColorValue = backColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = titleNameWidth,
        height = tdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        charColorValue = charColorValue,
        baseSize = titleFontSize,
        isBold = true
    )
)

internal val timeTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(),
    patternFillStyle = PatternFillStyle(
        backColorValue = backColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = timeWidth,
        height = tdHeight
    ),
    paragraphStyle = ParagraphStyle(
        charColorValue = charColorValue
    )
)