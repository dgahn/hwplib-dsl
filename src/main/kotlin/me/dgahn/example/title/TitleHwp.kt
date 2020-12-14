package me.dgahn.example.title

import me.dgahn.hwpdsl.SECTION
import me.dgahn.hwpdsl.table
import me.dgahn.hwpdsl.td
import me.dgahn.hwpdsl.tr

fun SECTION.title(data: TitleData) {
    table(rowSize = 1, colSize = 1, tableStyle = titleNameTableStyle) {
        tr {
            td(tdStyle = titleNameTdStyle) {
                + data.title
            }
        }
    }
}