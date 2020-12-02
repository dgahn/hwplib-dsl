package me.dgahn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine
import me.dgahn.fixture.hwpFile


class HwpBuilderKtTest : FunSpec({
    test("빈 파일의 Hwp을 만들 수 있다.") {
        val path = "sample/1-copy-sample.hwp"
        hwpFile.hwp(path = path) {
            println("아무 것도 안함")
        }

        val readHwp = readHwp(path)
        readHwp shouldNotBe null
    }

    test("hwp 태그를 사용할 수 있다.") {
        val path = "sample/2-body-sample.hwp"
        createHwp().hwp(path = path) {
            body {
                println("아무 것도 안함")
            }
        }

        val readHwp = readHwp(path)

        readHwp shouldNotBe null
    }

    test("hwp의 사이즈를 결정할 수 있다.") {
        val path = "sample/3-size-sample.hwp"
        createHwp().hwp(path = path) {
            body {
                paperSize(PaperSize.B4)
            }
        }

        val readHwp = readHwp(path)
        val csd = readHwp.bodyText.sectionList.first().getParagraph(0).controlList.first() as ControlSectionDefine

        csd.pageDef.paperWidth shouldBe 72852
        csd.pageDef.paperHeight shouldBe 103180
    }
})