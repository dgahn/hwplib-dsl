package me.dgahn.example.detail

import me.dgahn.hwpdsl.SECTION
import me.dgahn.hwpdsl.img
import me.dgahn.hwpdsl.mergeCell
import me.dgahn.hwpdsl.table
import me.dgahn.hwpdsl.td
import me.dgahn.hwpdsl.tr

fun SECTION.detail(detailData: DetailData) {
    val detailList = detailData.detailList
    detailList.forEachIndexed { index, detail ->
        val number = index + 1
        if (index % 4 == 0) {
            table(rowSize = 1, colSize = 1, tableStyle = subtitleNameTableStyle) {
                tr {
                    td(tdStyle = subtitleNameTdStyle) {
                        val start = index + 1
                        val end = if (detailList.size - start >= 4) start + 3 else detailList.size
                        +"상세한 사진(${index + 1}~$end)"
                    }
                }
            }
        }

        val detailViewTableStyle = if(number % 2 == 1) detailViewOddTableStyle else detailViewEvenTableStyle
        table(rowSize = 4, colSize = 2, tableStyle = detailViewTableStyle) {
            tr {
                td(tdStyle = detailViewImgTdStyle) {
                    img(
                        src = detail.imgSrc,
                        width = detailTdWidth.toInt(),
                        height = detailImgTdHeight.toInt(),
                    )
                }
                td(tdStyle = detailViewImgTdStyle)
            }
            tr {
                td(tdStyle = detailViewKeyTdStyle) {
                    +"첫 번째"
                }
                td(tdStyle = detailViewValueTdStyle) {
                    +detail.firstValue
                }
            }
            tr {
                td(tdStyle = detailViewKeyTdStyle) {
                    +"두 번째"
                }
                td(tdStyle = detailViewValueTdStyle) {
                    +detail.secondValue
                }
            }
            tr {
                td(tdStyle = detailViewKeyTdStyle) {
                    +"세 번째"
                }
                td(tdStyle = detailViewValueTdStyle) {
                    +detail.thirdValue
                }
            }
            mergeCell(
                startRow = 0,
                startCol = 0,
                rowSpan = 1,
                colSpan = 2
            )
        }
    }
}