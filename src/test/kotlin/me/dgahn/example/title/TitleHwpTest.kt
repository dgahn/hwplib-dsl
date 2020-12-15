package me.dgahn.example.title

import io.kotest.core.spec.style.FunSpec
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.hwpdsl.PaperSize
import me.dgahn.hwpdsl.body
import me.dgahn.hwpdsl.build
import me.dgahn.hwpdsl.createHwp
import me.dgahn.hwpdsl.hwp
import me.dgahn.hwpdsl.paperSize
import me.dgahn.hwpdsl.readHwp
import me.dgahn.hwpdsl.section
import java.time.ZonedDateTime

class TitleHwpTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("제목을 추가할 수 있다.") {
        val path = "sample/8-title-sample.hwp"
        val title = "오늘의 사진 목록"
        val createdTime = ZonedDateTime.now()
        val data = TitleData(title = title, createdTime = createdTime)
        hwpFile.createHwp().hwp {
            body {
                section {
                    title(data = data)
                }
            }
        }.paperSize(PaperSize.A4)
            .build(path)
    }
})