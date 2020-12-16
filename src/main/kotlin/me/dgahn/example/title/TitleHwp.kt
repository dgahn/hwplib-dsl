package me.dgahn.example.title

import me.dgahn.hwpdsl.SECTION
import me.dgahn.hwpdsl.mergeCell
import me.dgahn.hwpdsl.table
import me.dgahn.hwpdsl.td
import me.dgahn.hwpdsl.tr

fun SECTION.title(data: TitleData) {
    table(rowSize = 2, colSize = 2, tableStyle = titleNameTableStyle) {
        tr {
            td(tdStyle = titleNameTdStyle) {
                + data.title
            }
            td(tdStyle = firstTimeTdStyle) {
                + "이건 시간 : ${data.createdTime.toFormatString()}"
            }
        }
        tr {
            td(tdStyle = titleNameTdStyle)
            td(tdStyle = secondTimeTdStyle) {
                + "요것도 시간 : ${data.createdTime.toFormatString()}"
            }
        }
        mergeCell(startRow = 0, startCol = 0, rowSpan = 2, colSpan = 1)
    }
}