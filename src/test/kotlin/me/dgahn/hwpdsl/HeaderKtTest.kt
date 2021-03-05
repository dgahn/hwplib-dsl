package me.dgahn.hwpdsl

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.docinfo.parashape.Alignment

class HeaderKtTest : FunSpec({

    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("header에 글씨를 추가할 수 있다.") {
        val path = "sample/13-header-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    for (i in 1..100) {
                        +"테스트, 테스트"
                    }
                    header(
                        headerFooterStyle = HeaderFooterStyle(
                            paragraphStyle = ParagraphStyle(
                                paragraphAlignment = Alignment.Right
                            )
                        )
                    ) {
                        +"머리말 머리말 머리말"
                    }
                }
            }
        }.build(path)
    }

})