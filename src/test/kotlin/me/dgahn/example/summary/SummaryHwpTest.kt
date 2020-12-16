package me.dgahn.example.summary

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.fixture.summaryData
import me.dgahn.hwpdsl.PaperSize
import me.dgahn.hwpdsl.body
import me.dgahn.hwpdsl.build
import me.dgahn.hwpdsl.createHwp
import me.dgahn.hwpdsl.hwp
import me.dgahn.hwpdsl.paperSize
import me.dgahn.hwpdsl.readHwp
import me.dgahn.hwpdsl.section
import java.time.ZonedDateTime
import kotlin.random.Random

class SummaryHwpTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("요약 정보를 출력할 수 있다.") {
        val path = "sample/11-summary-sample.hwp"
        hwpFile.createHwp().hwp {
            body {
                section {
                    summary(summaryData)
                }
            }
        }.paperSize(PaperSize.A4)
            .build(path)
    }
})