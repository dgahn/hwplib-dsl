package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HeightCriterion
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HorzRelTo
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.ObjectNumberSort
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.RelativeArrange
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextFlowMethod
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextHorzArrange
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.VertRelTo
import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.WidthCriterion
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.ControlRectangle
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.GsoControl
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.GsoControlType
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.ShapeComponentNormal
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineArrowShape
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineArrowSize
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineEndShape
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineType
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.OutlineStyle
import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.shadowinfo.ShadowType
import kr.dogfoot.hwplib.`object`.docinfo.BinData
import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataCompress
import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataState
import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.ImageFillType
import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.PictureEffect
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

open class IMG(
    override val consumer: HwpTagConsumer<*>,
    override val builder: ImgBuilder
) : Tag

fun BODY.img(src: BufferedImage, width: Int, height: Int, block: IMG.() -> Unit = {}) = IMG(
    consumer = consumer,
    builder = ImgBuilder(hwpFile = consumer.hwpFile, src = src, width = width, height = height)
).visit(block)

class ImgBuilder(
    override val hwpFile: HWPFile,
    val width: Int,
    val height: Int,
    val top: Int = 0,
    val left: Int = 0,
    val format: String = "png",
    val src: BufferedImage,
    val tdBuilder: TdBuilder? = null
) : HwpTagBuilder {

    override fun build() {
        val streamIndex = hwpFile.binData.embeddedBinaryDataList.size + 1
        addBinDataInBody(hwpFile, format, src, streamIndex)
        addBinDataInDoc(hwpFile, format, streamIndex)

        val binDataID = hwpFile.docInfo.binDataList.size
        if (tdBuilder == null) {
            val shapePosition = Rectangle(top, left, width, height)
            addGsoControl(hwpFile, binDataID, shapePosition)
        } else {
            tdBuilder.setImageFill(binDataID)
            tdBuilder.setParagraphForCell("", tdBuilder.cell)
        }
    }

    private fun addBinDataInBody(hwpFile: HWPFile, format: String, img: BufferedImage, streamIndex: Int) {
        val streamName = "Bin${String.format("%04X", streamIndex)}.$format"
        val imgBinary = ByteArrayOutputStream().let {
            ImageIO.write(img, format, it)
            it.flush()
            val imgBinary = it.toByteArray() // you have the data in byte array
            it.close()
            imgBinary
        }

        hwpFile.binData.addNewEmbeddedBinaryData(streamName, imgBinary, BinDataCompress.ByStroageDefault)
    }

    private fun addBinDataInDoc(hwpFile: HWPFile, format: String, streamIndex: Int) {
        val bd = BinData()
        bd.property.type = BinDataType.Embedding
        bd.property.compress = BinDataCompress.ByStroageDefault
        bd.property.state = BinDataState.NotAcceess
        bd.binDataID = streamIndex
        bd.extensionForEmbedding = format
        hwpFile.docInfo.binDataList.add(bd)
    }

    private fun addGsoControl(hwpFile: HWPFile, binDataID: Int, shapePosition: Rectangle) {
        val rectangle: ControlRectangle = createRectangleControlAtFirstParagraph(hwpFile)
        setCtrlHeaderGso(rectangle, shapePosition)
        setShapeComponent(rectangle, shapePosition, binDataID)
        setShapeComponentRectangle(rectangle, shapePosition)
    }

    private fun createRectangleControlAtFirstParagraph(hwpFile: HWPFile): ControlRectangle {
        val firstSection = hwpFile.bodyText.sectionList.first()
        val firstParagraph = firstSection.getParagraph(0)

        // 문단에서 사각형 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
        firstParagraph.text.addExtendCharForGSO()

        // 문단에 사각형 컨트롤 추가한다.
        return firstParagraph.addNewGsoControl(GsoControlType.Rectangle) as ControlRectangle
    }

    private fun setCtrlHeaderGso(rectangle: GsoControl, shapePosition: Rectangle) {
        val instanceID = 0x5bb840e1
        val hdr = rectangle.header
        val prop = hdr.property
        prop.isLikeWord = true
        prop.isApplyLineSpace = false
        prop.vertRelTo = VertRelTo.Para
        prop.vertRelativeArrange = RelativeArrange.TopOrLeft
        prop.horzRelTo = HorzRelTo.Para
        prop.horzRelativeArrange = RelativeArrange.TopOrLeft
        prop.isVertRelToParaLimit = true
        prop.isAllowOverlap = true
        prop.widthCriterion = WidthCriterion.Absolute
        prop.heightCriterion = HeightCriterion.Absolute
        prop.isProtectSize = false
        prop.textFlowMethod = TextFlowMethod.TopAndBottom
        prop.textHorzArrange = TextHorzArrange.BothSides
        prop.objectNumberSort = ObjectNumberSort.Figure
        hdr.setyOffset(fromMM(shapePosition.y).toLong())
        hdr.setxOffset(fromMM(shapePosition.x).toLong())
        hdr.width = fromMM(shapePosition.width).toLong()
        hdr.height = fromMM(shapePosition.height).toLong()
        hdr.setzOrder(0)
        hdr.outterMarginLeft = 0
        hdr.outterMarginRight = 0
        hdr.outterMarginTop = 0
        hdr.outterMarginBottom = 0
        hdr.instanceId = instanceID.toLong()
        hdr.isPreventPageDivide = false
        hdr.explanation = null
    }

    private fun fromMM(mm: Int): Int {
        return if (mm == 0) {
            1
        } else (mm.toDouble() * 72000.0f / 254.0f + 0.5f).toInt()
    }

    private fun setShapeComponent(rectangle: GsoControl, shapePosition: Rectangle, binDataID: Int) {
        val sc = rectangle.shapeComponent as ShapeComponentNormal
        sc.offsetX = 0
        sc.offsetY = 0
        sc.groupingCount = 0
        sc.localFileVersion = 1
        sc.widthAtCreate = fromMM(shapePosition.width).toLong()
        sc.heightAtCreate = fromMM(shapePosition.height).toLong()
        sc.widthAtCurrent = fromMM(shapePosition.width).toLong()
        sc.heightAtCurrent = fromMM(shapePosition.height).toLong()
        sc.rotateAngle = 0
        sc.rotateXCenter = fromMM(shapePosition.width / 2)
        sc.rotateYCenter = fromMM(shapePosition.height / 2)
        sc.createLineInfo()
        val li = sc.lineInfo
        li.property.lineEndShape = LineEndShape.Flat
        li.property.startArrowShape = LineArrowShape.None
        li.property.startArrowSize = LineArrowSize.MiddleMiddle
        li.property.endArrowShape = LineArrowShape.None
        li.property.endArrowSize = LineArrowSize.MiddleMiddle
        li.property.isFillStartArrow = true
        li.property.isFillEndArrow = true
        li.property.lineType = LineType.None
        li.outlineStyle = OutlineStyle.Normal
        li.thickness = 0
        li.color.value = 0
        sc.createFillInfo()
        val fi = sc.fillInfo
        fi.type.setPatternFill(false)
        fi.type.setImageFill(true)
        fi.type.setGradientFill(false)
        fi.createImageFill()
        val imgF = fi.imageFill
        imgF.imageFillType = ImageFillType.FitSize
        imgF.pictureInfo.brightness = 0.toByte()
        imgF.pictureInfo.contrast = 0.toByte()
        imgF.pictureInfo.effect = PictureEffect.RealPicture
        imgF.pictureInfo.binItemID = binDataID
        sc.createShadowInfo()
        val si = sc.shadowInfo
        si.type = ShadowType.None
        si.color.value = 0xc4c4c4
        si.offsetX = 283
        si.offsetY = 283
        si.transparnet = 0.toShort()
        sc.setMatrixsNormal()
    }

    private fun setShapeComponentRectangle(rectangle: ControlRectangle, shapePosition: Rectangle) {
        val scr = rectangle.shapeComponentRectangle
        scr.roundRate = 0.toByte()
        scr.x1 = 0
        scr.y1 = 0
        scr.x2 = fromMM(shapePosition.width)
        scr.y2 = 0
        scr.x3 = fromMM(shapePosition.width)
        scr.y3 = fromMM(shapePosition.height)
        scr.x4 = 0
        scr.y4 = fromMM(shapePosition.height)
    }
}
