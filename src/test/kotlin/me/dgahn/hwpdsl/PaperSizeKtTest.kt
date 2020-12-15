package me.dgahn.hwpdsl

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine

class PaperSizeKtTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("hwp의 종이 사이즈를 수정할 수 있다.") {
        val path = "sample/3-size-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    + "이건 B4 사이즈"
                    + "알겠어"
                }
            }
        }.paperSize(PaperSize.B4).build(path)

        val readHwp = readHwp(path)
        val csd = readHwp.bodyText.sectionList.first().getParagraph(0).controlList.first() as ControlSectionDefine

        csd.pageDef.paperWidth shouldBe 72852
        csd.pageDef.paperHeight shouldBe 103180
    }
})