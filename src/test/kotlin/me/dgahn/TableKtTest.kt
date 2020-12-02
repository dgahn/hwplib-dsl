package me.dgahn

import io.kotest.core.spec.style.FunSpec
import me.dgahn.fixture.hwpFile
import me.dgahn.fixture.imgFile
import javax.imageio.ImageIO

class TableKtTest : FunSpec({
    test("hwp에 테이블을 삽입할 수 있다.") {
        val path = "sample/5-table-sample.hwp"
        hwpFile.hwp(path = path) {
            body {
                paperSize(PaperSize.A4)
                table(rowCount = 2, colCount = 2) {
                    tr {
                        td(hwpFile = hwpFile, row = 0, col = 0, "위")
                        td(hwpFile = hwpFile, row = 0, col = 1, "위")
                    }
                    tr {
                        td(hwpFile = hwpFile, row = 1, col = 0, "아래")
                        td(hwpFile = hwpFile, row = 1, col = 1, "아래")
                    }
                }
            }
        }
    }

    test("hwp에 테이블과 이미지를 삽입할 수 있다.") {
        val path = "sample/6-table-img-sample.hwp"
        val img = ImageIO.read(imgFile)
        hwpFile.hwp(path = path) {
            body {
                img(width = 120, height = 120, img = img)
                paperSize(PaperSize.B4)
                table(rowCount = 2, colCount = 2) {
                    tr {
                        td(hwpFile = hwpFile, row = 0, col = 0, "위")
                        td(hwpFile = hwpFile, row = 0, col = 1, "위")
                    }
                    tr {
                        td(hwpFile = hwpFile, row = 1, col = 0, "아래")
                        td(hwpFile = hwpFile, row = 1, col = 1, "아래")
                    }
                }
                img(width = 120, height = 120, img = img)
            }
        }
    }
})