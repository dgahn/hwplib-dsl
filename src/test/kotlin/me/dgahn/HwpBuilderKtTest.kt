package me.dgahn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO


class HwpBuilderKtTest : FunSpec({

    test("빈 파일의 Hwp을 만들 수 있다.") {
        val path = "sample-1.hwp"
        createHwp().hwp(path = path) {
            println("아무 것도 안함")
        }

        val readHwp = readHwp(path)

        readHwp shouldNotBe null

        val file = File(path)
        file.deleteOnExit()
    }

    test("hwp 태그 안에 body를 넣을 수 있다.") {
        val path = "sample-1.hwp"
        createHwp().hwp(path = path) {
            body {
                println("아무 것도 안함")
            }
        }

        val readHwp = readHwp(path)

        readHwp shouldNotBe null

        val file = File(path)
        file.deleteOnExit()
    }

    test("hwp의 사이즈를 결정할 수 있다.") {
        val path = "sample-2.hwp"
        createHwp().hwp(path = path) {
            body {
                paperSize(PaperSize.B4)
                println("아무 것도 안함")
            }
        }

        val readHwp = readHwp(path)
        val csd = readHwp.bodyText.sectionList.first().getParagraph(0).controlList.first() as ControlSectionDefine

        csd.pageDef.paperWidth shouldBe 72852
        csd.pageDef.paperHeight shouldBe 103180

        val file = File(path)
        file.deleteOnExit()
    }

    test("hwp에 이미지를 삽입할 수 있다.") {
        val path = "sample-2.hwp"
        val imgPath = "satellite.png"
        val imgFile = javaClass.classLoader.getResource(imgPath)
        val expected = ImageIO.read(imgFile)
        createHwp().hwp(path = path) {
            body {
                paperSize(PaperSize.B4)
                img(width = 120, height = 120, img = expected)
            }
        }

        val readHwp = readHwp(path)
        val actual = ImageIO.read(ByteArrayInputStream(readHwp.binData.embeddedBinaryDataList.first().data))

        actual.raster.toString() shouldBe expected.raster.toString()
        actual.alphaRaster.toString() shouldBe expected.alphaRaster.toString()

        val file = File(path)
        file.deleteOnExit()
    }

    test("hwp에 테이블을 삽입할 수 있다.") {
        val path = "sample-3.hwp"
        val newFile = createHwp()
        newFile.hwp(path = path) {
            body {
                paperSize(PaperSize.A4)
                table(rowCount = 3, colCount = 4) {
                    tr {
                        td(hwpFile = newFile, row = 0, col = 0, "위")
                        td(hwpFile = newFile, row = 0, col = 1, "위")
                        td(hwpFile = newFile, row = 0, col = 2, "위")
                        td(hwpFile = newFile, row = 0, col = 3, "위")
                    }
                    tr {
                        td(hwpFile = newFile, row = 1, col = 0, "중간")
                        td(hwpFile = newFile, row = 1, col = 1, "중간")
                        td(hwpFile = newFile, row = 1, col = 2, "중간")
                        td(hwpFile = newFile, row = 1, col = 3, "중간")
                    }
                    tr {
                        td(hwpFile = newFile, row = 2, col = 0, "마지막")
                        td(hwpFile = newFile, row = 2, col = 1, "마지막")
                        td(hwpFile = newFile, row = 2, col = 2, "마지막")
                        td(hwpFile = newFile, row = 2, col = 3, "마지막")
                    }
                }
            }
        }

        val readHwp = readHwp(path)
    }

})