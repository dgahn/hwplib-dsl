package me.dgahn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.fixture.imgFile
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class ImgKtTest : FunSpec({
    lateinit var sample: String
    lateinit var samplePath: String
    lateinit var hwpFile: HWPFile

    beforeEach {
        sample = "sample.hwp"
        samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
        hwpFile = readHwp(samplePath)
    }

    test("hwp에 이미지를 삽입할 수 있다.") {
        val path = "sample/4-image-sample.hwp"
        val expected = ImageIO.read(imgFile)
        hwpFile.createHwp().hwp {
            body {
                paperSize(PaperSize.B4)
                img(width = 120, height = 120, src = expected)
                + "\n위성 이미지입니다."
            }
        }.build(path)

        val readHwp = readHwp(path)
        val actual = ImageIO.read(ByteArrayInputStream(readHwp.binData.embeddedBinaryDataList.first().data))

        actual.raster.toString() shouldBe expected.raster.toString()
        actual.alphaRaster.toString() shouldBe expected.alphaRaster.toString()
    }
})
