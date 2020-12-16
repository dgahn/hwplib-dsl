package me.dgahn.example

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.fixture.overviewData
import me.dgahn.fixture.summaryData
import me.dgahn.fixture.titleData
import me.dgahn.hwpdsl.readHwp

class HwpExampleBuilderTest : FunSpec({
    val hwpExampleBuilder = HwpExampleBuilder()
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("예제 파일을 출력할 수 있다.") {
        val path = "sample/10-example-file.hwp"

        hwpExampleBuilder
            .hwpFile(hwpFile)
            .titleData(titleData)
            .overviewData(overviewData)
            .summaryData(summaryData)
            .path(path)
            .build()
    }

})