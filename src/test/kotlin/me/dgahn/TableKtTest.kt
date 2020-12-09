package me.dgahn

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.fixture.imgFile
import javax.imageio.ImageIO

class TableKtTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("hwp에 테이블을 삽입할 수 있다.") {
        val path = "sample/5-table-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                paperSize(PaperSize.A4)
                table(rowSize = 2, colSize = 2) {
                    tr {
                        td {
                            + "이건 위 쪽"
                            + "이건 위 쪽"
                        }
                        td {
                            + "이건 위 쪽"
                            + "이건 위 쪽"
                        }
                    }
                    tr {
                        td {
                            + "이건 아래 쪽"
                            + "이건 아래 쪽"
                        }
                        td {
                            + "이건 아래 쪽"
                        }
                    }
                }
            }
        }.build(path)
    }

    test("row 사이즈를 초과하면 예외가 발생한다.") {
        val path = "sample/5-table-sample.hwp"
        shouldThrow<RuntimeException> {
            hwpFile.createHwp().hwp {
                body {
                    paperSize(PaperSize.A4)
                    table(rowSize = 2, colSize = 2) {
                        tr {
                            td()
                            td()
                        }
                        tr {
                            td()
                            td()
                        }
                        tr {
                            td()
                            td()
                        }
                    }
                }
            }.build(path)
        }
    }

    test("col 사이즈를 초과하면 예외가 발생한다.") {
        val path = "sample/5-table-sample.hwp"
        shouldThrow<RuntimeException> {
            hwpFile.createHwp().hwp {
                body {
                    paperSize(PaperSize.A4)
                    table(rowSize = 2, colSize = 2) {
                        tr {
                            td()
                            td()
                            td()
                        }
                        tr {
                            td()
                            td()
                            td()
                        }
                    }
                }
            }.build(path)
        }
    }

    test("hwp에 테이블과 이미지를 삽입할 수 있다.") {
        val path = "sample/6-table-img-sample.hwp"
        val img = ImageIO.read(imgFile)
//        hwpFile.createHwp(path = path) {
//            body {
//                img(width = 120, height = 120, img = img)
//                paperSize(PaperSize.B4)
//                table(rowCount = 2, colCount = 2) {
//                    tr {
//                        td(hwpFile = hwpFile, row = 0, col = 0, "위")
//                        td(hwpFile = hwpFile, row = 0, col = 1, "위")
//                    }
//                    tr {
//                        td(hwpFile = hwpFile, row = 1, col = 0, "아래")
//                        td(hwpFile = hwpFile, row = 1, col = 1, "아래")
//                    }
//                }
//                img(width = 120, height = 120, img = img)
//            }
//        }
    }
})