package me.dgahn.example.detail

import kr.dogfoot.hwplib.`object`.docinfo.parashape.Alignment
import me.dgahn.hwpdsl.BorderFillStyle
import me.dgahn.hwpdsl.CtrlHeaderStyle
import me.dgahn.hwpdsl.ListHeaderStyle
import me.dgahn.hwpdsl.ParagraphStyle
import me.dgahn.hwpdsl.PatternFillStyle
import me.dgahn.hwpdsl.TableStyle
import me.dgahn.hwpdsl.TdStyle

private const val pageWidth = 200.0
private const val subtitleTableWidth = pageWidth
private const val subtitleBackColorValue: Long = 0x00EFE7DB
private const val subtitleTdHeight: Double = 10.0
private const val subtitleTitleFontSize: Double = 12.0
private const val subtitleMarginBottom: Double = 1.0
private const val detailTableWidth = 98.0
internal const val detailTdWidth = detailTableWidth / 2
private const val detailTableHeight = 120.0
internal const val detailImgTdHeight = detailTableHeight * 4 / 5
private const val detailStringTdHeight = detailTableHeight * 1 / 15
private const val detailKeyTdWidth = 18.0
private const val detailKeyTdColorValue: Long = 0x00EFE7DB
private const val detailValueTdWidth = detailTableWidth - detailKeyTdWidth

internal val subtitleNameTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = subtitleTableWidth,
        height = subtitleTdHeight,
        outterMarginBottom = subtitleMarginBottom
    )
)

internal val subtitleNameTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(),
    patternFillStyle = PatternFillStyle(
        backColorValue = subtitleBackColorValue
    ),
    listHeaderStyle = ListHeaderStyle(
        width = subtitleTableWidth,
        height = subtitleTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Left,
        baseSize = subtitleTitleFontSize,
        isBold = true
    )
)

internal val detailViewOddTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = detailTableWidth,
        height = detailTableHeight,
        outterMarginRight = 1.5,
        outterMarginBottom = 15.0
    )
)

internal val detailViewEvenTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(
        width = detailTableWidth,
        height = detailTableHeight,
        outterMarginLeft = 1.5,
        outterMarginBottom = 15.0
    )
)

internal val detailViewImgTdStyle = TdStyle(
    listHeaderStyle = ListHeaderStyle(
        width = detailTdWidth,
        height = detailImgTdHeight,
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center
    )
)

internal val detailViewKeyTdStyle = TdStyle(
    listHeaderStyle = ListHeaderStyle(
        width = detailKeyTdWidth,
        height = detailStringTdHeight
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center
    ),
    patternFillStyle = PatternFillStyle(
        backColorValue = detailKeyTdColorValue
    )
)

internal val detailViewValueTdStyle = TdStyle(
    listHeaderStyle = ListHeaderStyle(
        width = detailValueTdWidth,
        height = detailStringTdHeight
    ),
    paragraphStyle = ParagraphStyle(
        paragraphAlignment = Alignment.Center
    )
)
