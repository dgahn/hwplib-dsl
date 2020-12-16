package me.dgahn.example.detail

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.fixture.detailData
import me.dgahn.hwpdsl.PaperSize
import me.dgahn.hwpdsl.body
import me.dgahn.hwpdsl.build
import me.dgahn.hwpdsl.createHwp
import me.dgahn.hwpdsl.hwp
import me.dgahn.hwpdsl.paperSize
import me.dgahn.hwpdsl.readHwp
import me.dgahn.hwpdsl.section

class DetailHwpKtTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("상세 뷰를 추가할 수 있다.") {
        val path = "sample/12-detail-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    detail(detailData)
                }
            }
        }.paperSize(PaperSize.A4)
            .build(path)
    }
})