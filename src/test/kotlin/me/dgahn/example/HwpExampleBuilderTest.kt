package me.dgahn.example

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.example.overview.OverviewData
import me.dgahn.example.title.TitleData
import me.dgahn.fixture.imgFile
import me.dgahn.hwpdsl.readHwp
import java.time.ZonedDateTime
import javax.imageio.ImageIO

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
        val titleData = TitleData(
            title = "오늘의 사진",
            createdTime = ZonedDateTime.now()
        )
        val overviewData = OverviewData(
            src = ImageIO.read(imgFile)
        )

        hwpExampleBuilder
            .hwpFile(hwpFile)
            .titleData(titleData)
            .overviewData(overviewData)
            .path(path)
            .build()
    }

})