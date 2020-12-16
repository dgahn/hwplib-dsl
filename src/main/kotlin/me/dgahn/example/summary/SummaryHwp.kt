package me.dgahn.example.summary

import me.dgahn.hwpdsl.SECTION
import me.dgahn.hwpdsl.table
import me.dgahn.hwpdsl.td
import me.dgahn.hwpdsl.tr

fun SECTION.summary(sampleData: SummaryData) {
    table(rowSize = 1, colSize = 6, tableStyle = titleNameTableStyle) {
        tr {
            td(tdStyle = thFirstTdStyle) {
                +"#"
            }
            td(tdStyle = thSecondTdStyle) {
                +"오 늘 사 진"
            }
            td(tdStyle = thThirdTdStyle) {
                +"촬 영 시 간"
            }
            td(tdStyle = thForthTdStyle) {
                +"이 름"
            }
            td(tdStyle = thFifthTdStyle) {
                +"그냥 수"
            }
            td(tdStyle = thSixthTdStyle) {
                +"그 냥 내 용"
            }
        }
    }

    // #
    sampleData.summary.forEach {
        table(rowSize = 1, colSize = 1, tableStyle = getFirstThTableStyle(it.triple.size)) {
            tr {
                td(tdStyle = getFirstTdTableStyle(it.triple.size)) {
                    +"${it.index}"
                }
            }
        }

        // 오늘 사진
        table(rowSize = 1, colSize = 1, tableStyle = getSecondThTableStyle(it.triple.size)) {
            tr {
                td(tdStyle = getSecondTdTableStyle(it.triple.size)) {
                    +it.name
                }
            }
        }

        // 촬영 시간
        table(rowSize = it.triple.size, colSize = 1, tableStyle = thirdTdTableStyle) {
            it.triple.forEach {
                tr {
                    td(tdStyle = thirdTdStyle) {
                        +it.first.toFormatString()
                    }
                }
            }
        }

        // 이름
        table(rowSize = it.triple.size, colSize = 1, tableStyle = forthThTableStyle) {
            it.triple.forEach {
                tr {
                    td(tdStyle = forthTdStyle) {
                        +it.second
                    }
                }
            }
        }

        table(rowSize = it.triple.size, colSize = 1, tableStyle = fifthThTableStyle) {
            it.triple.forEach {
                tr {
                    td(tdStyle = fifthTdStyle) {
                        +"${it.third}"
                    }
                }
            }
        }

        table(rowSize = 1, colSize = 1, tableStyle = getSixThTableStyle(it.triple.size)) {
            tr {
                td(tdStyle = getSixThTdTableStyle(it.triple.size)) {
                    +it.description
                }
            }
        }
    }
}