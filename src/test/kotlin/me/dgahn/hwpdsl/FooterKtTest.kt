package me.dgahn.hwpdsl

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.docinfo.parashape.Alignment

class FooterKtTest : FunSpec({

    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("footer에 글씨를 추가할 수 있다.") {
        val path = "sample/14-footer-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    for (i in 1..100) {
                        +"테스트, 테스트"
                    }
                    footer(
                        headerFooterStyle = HeaderFooterStyle(
                            paragraphStyle = ParagraphStyle(
                                paragraphAlignment = Alignment.Right
                            )
                        )
                    ) {
                        +"꼬리말 꼬리말 꼬리말"
                    }
                }
            }
        }.build(path)
    }

})