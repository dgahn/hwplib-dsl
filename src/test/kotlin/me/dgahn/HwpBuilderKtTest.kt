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
                section {
                    +"테스트1 글씨글씨"
                    +"테스트2 글씨글씨"
                    +"테스트3 글씨글씨"
                }
            }
        }.build(path)

        val readHwp = readHwp(path)

        readHwp shouldNotBe null
    }
})
