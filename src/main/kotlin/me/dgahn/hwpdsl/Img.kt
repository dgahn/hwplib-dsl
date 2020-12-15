package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.Section
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

fun BODY.img(
    src: BufferedImage,
    width: Int,
    height: Int,
    imgStyle: ImgStyle = ImgStyle(
        shapeComponentRectangleStyle = ShapeComponentRectangleStyle(
            x2 = width,
            x3 = width,
            y3 = height,
            y4 = height
        )
    ),
    block: IMG.() -> Unit = {}
) = IMG(
    consumer = consumer,
    builder = ImgBuilder(
        hwpFile = consumer.hwpFile,
        section = consumer.currentSection,
        src = src,
        width = width,
        height = height,
        imgStyle = imgStyle
    )
).visit(block)

fun SECTION.img(
    src: BufferedImage,
    width: Int,
    height: Int,
    imgStyle: ImgStyle,
    block: IMG.() -> Unit = {}
) = IMG(
    consumer = consumer,
    builder = ImgBuilder(
        hwpFile = consumer.hwpFile,
        section = consumer.currentSection,
        src = src,
        width = width,
        height = height,
        imgStyle = imgStyle
    )
).visit(block)

class ImgBuilder(
    override val hwpFile: HWPFile,
    val section: Section,
    val width: Int,
    val height: Int,
    val top: Int = 0,
    val left: Int = 0,
    val imgStyle: ImgStyle = ImgStyle(),
    val src: BufferedImage,
    val tdBuilder: TdBuilder? = null
) : HwpTagBuilder {
    private val binDataStyle: BinDataStyle
        get() = imgStyle.binDataStyle
    private val ctrlHeaderGsoStyle: CtrlHeaderGsoStyle
        get() = imgStyle.ctrlHeaderGsoStyle
    private val shapeComponentStyle: ShapeComponentStyle
        get() = imgStyle.shapeComponentStyle
    private val lineInfoStyle: LineInfoStyle
        get() = imgStyle.lineInfoStyle
    private val imgFillStyle: ImageFillStyle
        get() = imgStyle.imgFillStyle
    private val shadowInfoStyle: ShadowInfoStyle
        get() = imgStyle.shadowInfoStyle
    private val shapeComponentRectangleStyle: ShapeComponentRectangleStyle
        get() = imgStyle.shapeComponentRectangleStyle
    private val paragraphStyle: ParagraphStyle
        get() = imgStyle.paragraphStyle

    override fun build() {
        val streamIndex = hwpFile.binData.embeddedBinaryDataList.size + 1
        addBinDataInBody(streamIndex)
        addBinDataInDoc(streamIndex)

        val binDataID = hwpFile.docInfo.binDataList.size
        if (tdBuilder == null) {
            val shapePosition = Rectangle(top, left, width, height)
            addGsoControl(binDataID, shapePosition)
        } else {
            tdBuilder.setImageFill(binDataID)
            tdBuilder.setParagraphForCell("", tdBuilder.cell)
        }
    }

    override fun completed() = Unit

    private fun addBinDataInBody(streamIndex: Int) {
        val streamName = "Bin${String.format("%04X", streamIndex)}.${binDataStyle.format}"
        val imgBinary = ByteArrayOutputStream().let {
            ImageIO.write(src, binDataStyle.format, it)
            it.flush()
            val imgBinary = it.toByteArray() // you have the data in byte array
            it.close()
            imgBinary
        }

        hwpFile.binData.addNewEmbeddedBinaryData(streamName, imgBinary, BinDataCompress.ByStroageDefault)
    }

    private fun addBinDataInDoc(streamIndex: Int) {
        val bd = BinData()
        bd.property.type = binDataStyle.type
        bd.property.compress = binDataStyle.compress
        bd.property.state = binDataStyle.state
        bd.binDataID = streamIndex
        bd.extensionForEmbedding = binDataStyle.format
        hwpFile.docInfo.binDataList.add(bd)
    }

    private fun addGsoControl(binDataID: Int, shapePosition: Rectangle) {
        val rectangle: ControlRectangle = createRectangleControlAtFirstParagraph()
        setCtrlHeaderGso(rectangle, shapePosition)
        setShapeComponent(rectangle, shapePosition, binDataID)
        setShapeComponentRectangle(rectangle)
    }

    private fun createRectangleControlAtFirstParagraph(): ControlRectangle {
        val firstParagraph = runCatching { section.getParagraph(0) }.getOrElse {
            section.addNewParagraph().apply {
                setParagraph(hwpFile = hwpFile, content = "", paragraphStyle = paragraphStyle)
            }
        }

        // 문단에서 사각형 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
        firstParagraph.text.addExtendCharForGSO()

        // 문단에 사각형 컨트롤 추가한다.
        return firstParagraph.addNewGsoControl(GsoControlType.Rectangle) as ControlRectangle
    }

    private fun setCtrlHeaderGso(rectangle: GsoControl, shapePosition: Rectangle) {
        val hdr = rectangle.header
        val prop = hdr.property
        prop.isLikeWord = ctrlHeaderGsoStyle.isLikeWord
        prop.isApplyLineSpace = ctrlHeaderGsoStyle.isApplyLineSpace
        prop.vertRelTo = ctrlHeaderGsoStyle.vertRelTo
        prop.vertRelativeArrange = ctrlHeaderGsoStyle.vertRelativeArrange
        prop.horzRelTo = ctrlHeaderGsoStyle.horzRelTo
        prop.horzRelativeArrange = ctrlHeaderGsoStyle.horzRelativeArrange
        prop.isVertRelToParaLimit = ctrlHeaderGsoStyle.isVertRelToParaLimit
        prop.isAllowOverlap = ctrlHeaderGsoStyle.isAllowOverlap
        prop.widthCriterion = ctrlHeaderGsoStyle.widthCriterion
        prop.heightCriterion = ctrlHeaderGsoStyle.heightCriterion
        prop.isProtectSize = ctrlHeaderGsoStyle.isProtectSize
        prop.textFlowMethod = ctrlHeaderGsoStyle.textFlowMethod
        prop.textHorzArrange = ctrlHeaderGsoStyle.textHorzArrange
        prop.objectNumberSort = ctrlHeaderGsoStyle.objectNumberSort
        hdr.setyOffset(fromMM(shapePosition.y).toLong())
        hdr.setxOffset(fromMM(shapePosition.x).toLong())
        hdr.width = fromMM(shapePosition.width).toLong()
        hdr.height = fromMM(shapePosition.height).toLong()
        hdr.setzOrder(ctrlHeaderGsoStyle.zOrder)
        hdr.outterMarginLeft = ctrlHeaderGsoStyle.outterMarginLeft
        hdr.outterMarginRight = ctrlHeaderGsoStyle.outterMarginRight
        hdr.outterMarginTop = ctrlHeaderGsoStyle.outterMarginTop
        hdr.outterMarginBottom = ctrlHeaderGsoStyle.outterMarginBottom
        hdr.instanceId = ctrlHeaderGsoStyle.instanceId
        hdr.isPreventPageDivide = ctrlHeaderGsoStyle.isPreventPageDivide
        hdr.explanation = ctrlHeaderGsoStyle.explanation
    }

    private fun fromMM(mm: Int): Int = if (mm == 0) 1 else (mm.toDouble() * 72000.0f / 254.0f + 0.5f).toInt()

    private fun setShapeComponent(rectangle: GsoControl, shapePosition: Rectangle, binDataID: Int) {
        val sc = rectangle.shapeComponent as ShapeComponentNormal
        sc.offsetX = shapeComponentStyle.offsetX
        sc.offsetY = shapeComponentStyle.offsetY
        sc.groupingCount = shapeComponentStyle.groupingCount
        sc.localFileVersion = shapeComponentStyle.localFileVersion
        sc.widthAtCreate = fromMM(shapePosition.width).toLong()
        sc.heightAtCreate = fromMM(shapePosition.height).toLong()
        sc.widthAtCurrent = fromMM(shapePosition.width).toLong()
        sc.heightAtCurrent = fromMM(shapePosition.height).toLong()
        sc.rotateAngle = shapeComponentStyle.rotateAngle
        sc.rotateXCenter = fromMM(shapePosition.width / 2)
        sc.rotateYCenter = fromMM(shapePosition.height / 2)
        sc.createLineInfo()
        val li = sc.lineInfo
        li.property.lineEndShape = lineInfoStyle.lineEndShape
        li.property.startArrowShape = lineInfoStyle.startArrowShape
        li.property.startArrowSize = lineInfoStyle.startArrowSize
        li.property.endArrowShape = lineInfoStyle.endArrowShape
        li.property.endArrowSize = lineInfoStyle.endArrowSize
        li.property.isFillStartArrow = lineInfoStyle.isFillStartArrow
        li.property.isFillEndArrow = lineInfoStyle.isFillEndArrow
        li.property.lineType = lineInfoStyle.lineType
        li.outlineStyle = lineInfoStyle.outlineStyle
        li.thickness = lineInfoStyle.thickness
        li.color.value = lineInfoStyle.colorValue
        sc.createFillInfo()
        val fi = sc.fillInfo
        fi.type.setPatternFill(false)
        fi.type.setImageFill(true)
        fi.type.setGradientFill(false)
        fi.createImageFill()
        val imgF = fi.imageFill
        imgF.imageFillType = imgFillStyle.imageFillType
        imgF.pictureInfo.brightness = imgFillStyle.brightness
        imgF.pictureInfo.contrast = imgFillStyle.contrast
        imgF.pictureInfo.effect = imgFillStyle.effect
        imgF.pictureInfo.binItemID = binDataID
        sc.createShadowInfo()
        val si = sc.shadowInfo
        si.type = shadowInfoStyle.type
        si.color.value = shadowInfoStyle.colorValue
        si.offsetX = shadowInfoStyle.offsetX
        si.offsetY = shadowInfoStyle.offsetY
        si.transparnet = shadowInfoStyle.transparnet
        sc.setMatrixsNormal()
    }

    private fun setShapeComponentRectangle(rectangle: ControlRectangle) {
        val scr = rectangle.shapeComponentRectangle
        scr.roundRate = 0.toByte()
        scr.x1 = fromMM(shapeComponentRectangleStyle.x1)
        scr.y1 = fromMM(shapeComponentRectangleStyle.y1)
        scr.x2 = fromMM(shapeComponentRectangleStyle.x2)
        scr.y2 = fromMM(shapeComponentRectangleStyle.y2)
        scr.x3 = fromMM(shapeComponentRectangleStyle.x3)
        scr.y3 = fromMM(shapeComponentRectangleStyle.y3)
        scr.x4 = fromMM(shapeComponentRectangleStyle.x4)
        scr.y4 = fromMM(shapeComponentRectangleStyle.y4)
    }
}
