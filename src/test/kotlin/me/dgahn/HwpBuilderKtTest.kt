package me.dgahn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine


class HwpBuilderKtTest : FunSpec({

    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("빈 파일의 Hwp을 만들 수 있다.") {
        val path = "sample/1-copy-sample.hwp"
        hwpFile.createHwp().hwp {
            println("아무 것도 안함")
        }.build(path)

        val readHwp = readHwp(path)
        readHwp shouldNotBe null
    }

    test("body 태그 안에 글씨를 추가할 수 있다.") {
        val path = "sample/2-body-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                + "테스트1 글씨글씨\n"
                + "테스트2 글씨글씨\n"
                + "테스트3 글씨글씨"
            }
        }.build(path)

        val readHwp = readHwp(path)

        readHwp shouldNotBe null
    }

    test("hwp의 사이즈를 결정할 수 있다.") {
        val path = "sample/3-size-sample.hwp"
        hwpFile.createHwp().hwp {
            paperSize(PaperSize.B4)
            body {
                + "이건 B4 사이즈의 한글 파일이야\n"
                + "알겠어"
            }
        }.build(path)

        val readHwp = readHwp(path)
        val csd = readHwp.bodyText.sectionList.first().getParagraph(0).controlList.first() as ControlSectionDefine

        csd.pageDef.paperWidth shouldBe 72852
        csd.pageDef.paperHeight shouldBe 103180
    }
})