package me.dgahn.example.title

import kr.dogfoot.hwplib.`object`.docinfo.borderfill.BorderType
import me.dgahn.hwpdsl.BorderFillStyle
import me.dgahn.hwpdsl.CtrlHeaderStyle
import me.dgahn.hwpdsl.ListHeaderStyle
import me.dgahn.hwpdsl.PatternFillStyle
import me.dgahn.hwpdsl.TableRecordStyle
import me.dgahn.hwpdsl.TableStyle
import me.dgahn.hwpdsl.TdStyle

private const val titleNameWidth = 100.0

internal val titleNameTableStyle = TableStyle(
    ctrlHeaderStyle = CtrlHeaderStyle(width = titleNameWidth),
    tableRecordStyle = TableRecordStyle()
)

internal val titleNameTdStyle = TdStyle(
    borderFileStyle = BorderFillStyle(),
    patternFillStyle = PatternFillStyle(
        backColorValue = 0x005c4719
    ),
    listHeaderStyle = ListHeaderStyle(width = titleNameWidth)
)