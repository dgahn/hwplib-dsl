package me.dgahn.example.overview

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.fixture.imgFile
import me.dgahn.fixture.overviewData
import me.dgahn.hwpdsl.PaperSize
import me.dgahn.hwpdsl.body
import me.dgahn.hwpdsl.build
import me.dgahn.hwpdsl.createHwp
import me.dgahn.hwpdsl.hwp
import me.dgahn.hwpdsl.paperSize
import me.dgahn.hwpdsl.readHwp
import me.dgahn.hwpdsl.section
import javax.imageio.ImageIO


class OverviewHwpKtTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("오버 뷰를 추가할 수 있다.") {
        val path = "sample/9-overview-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    overview(overviewData)
                }
            }
        }.paperSize(PaperSize.A4)
            .build(path)
    }
})