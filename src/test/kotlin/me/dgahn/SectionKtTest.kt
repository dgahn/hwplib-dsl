package me.dgahn

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker

class SectionKtTest: FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("hwp에 Section을 추가할 수 있다.") {
        val path = "sample/7-section-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    + "첫번째 Section 입니다."
                }
                section {
                    + "두번째 Section 입니다."
                }
            }
        }.build(path)
    }

    test("빈파일에 Section을 추가할 수 있다.") {
        val path = "sample/7-section-sample-1.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    + "첫번째 Section 입니다."
                    + "첫번째 Section 입니다."
                }
                section {
                    + "두번째 Section 입니다."
                    + "두번째 Section 입니다."
                }
            }
        }.build(path)
    }
})