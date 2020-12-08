package me.dgahn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import me.dgahn.fixture.imgFile
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class ImgKtTest : FunSpec({
    test("hwp에 이미지를 삽입할 수 있다.") {
        val path = "sample/4-image-sample.hwp"
        val expected = ImageIO.read(imgFile)
//        createHwp().createHwp(path = path) {
//            body {
//                paperSize(PaperSize.B4)
//                img(width = 120, height = 120, img = expected)
//            }
//        }

        val readHwp = readHwp(path)
        val actual = ImageIO.read(ByteArrayInputStream(readHwp.binData.embeddedBinaryDataList.first().data))

        actual.raster.toString() shouldBe expected.raster.toString()
        actual.alphaRaster.toString() shouldBe expected.alphaRaster.toString()
    }
})