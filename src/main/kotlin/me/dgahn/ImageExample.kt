//package me.dgahn
//
//import kr.dogfoot.hwplib.`object`.HWPFile
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HeightCriterion
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.HorzRelTo
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.ObjectNumberSort
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.RelativeArrange
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextFlowMethod
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.TextHorzArrange
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.VertRelTo
//import kr.dogfoot.hwplib.`object`.bodytext.control.ctrlheader.gso.WidthCriterion
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.ControlRectangle
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.GsoControlType
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.ShapeComponentNormal
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineArrowShape
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineArrowSize
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineEndShape
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.LineType
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.lineinfo.OutlineStyle
//import kr.dogfoot.hwplib.`object`.bodytext.control.gso.shapecomponent.shadowinfo.ShadowType
//import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataCompress
//import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataState
//import kr.dogfoot.hwplib.`object`.docinfo.bindata.BinDataType
//import kr.dogfoot.hwplib.`object`.docinfo.borderfill.fillinfo.ImageFillType
//import kr.dogfoot.hwplib.reader.HWPReader
//import kr.dogfoot.hwplib.writer.HWPWriter
//import java.awt.Rectangle
//import java.io.FileInputStream
//import java.io.IOException
//import java.io.InputStream
//
//
//class Inserting_Image {
//    private val imageFilePath = "sample_hwp" + File.separator.toString() + "sample.jpg"
//    private val imageFileExt = "jpg"
//    private val compressMethod = BinDataCompress.ByStroageDefault
//    private val instanceID = 0x5bb840e1
//    private var hwpFile: HWPFile? = null
//    private var streamIndex = 0
//    private var binDataID = 0
//    private var rectangle: ControlRectangle? = null
//    private val shapePosition = Rectangle(50, 50, 100, 100)
//    @Throws(IOException::class)
//    private fun insertShapeWithImage(hwpFile: HWPFile) {
//        this.hwpFile = hwpFile
//        addBinData()
//        binDataID = addBinDataInDocInfo(streamIndex)
//        addGsoControl()
//    }
//
//    @Throws(IOException::class)
//    private fun addBinData() {
//        streamIndex = hwpFile!!.binData.embeddedBinaryDataList.size + 1
//        val streamName = streamName
//        val fileBinary = loadFile()
//        hwpFile!!.binData.addNewEmbeddedBinaryData(streamName, fileBinary, compressMethod)
//    }
//
//    private val streamName: String
//        private get() = "Bin" + String.format("%04X", streamIndex) + "." + imageFileExt
//
//    @Throws(IOException::class)
//    private fun loadFile(): ByteArray {
//        val file = File(imageFilePath)
//        val buffer = ByteArray(file.length() as Int)
//        var ios: InputStream? = null
//        try {
//            ios = FileInputStream(file)
//            ios!!.read(buffer)
//        } finally {
//            try {
//                ios?.close()
//            } catch (e: IOException) {
//            }
//        }
//        return buffer
//    }
//
//    private fun addBinDataInDocInfo(streamIndex: Int): Int {
//        val bd = BinData()
//        bd.getProperty().setType(BinDataType.Embedding)
//        bd.getProperty().setCompress(compressMethod)
//        bd.getProperty().setState(BinDataState.NotAcceess)
//        bd.setBinDataID(streamIndex)
//        bd.setExtensionForEmbedding(imageFileExt)
//        hwpFile!!.docInfo.binDataList.add(bd)
//        return hwpFile!!.docInfo.binDataList.size
//    }
//
//    private fun addGsoControl() {
//        createRectangleControlAtFirstParagraph()
//        setCtrlHeaderGso()
//        setShapeComponent()
//        setShapeComponentRectangle()
//    }
//
//    private fun createRectangleControlAtFirstParagraph() {
//        val firstSection: Section = hwpFile!!.bodyText.sectionList[0]
//        val firstParagraph: Paragraph = firstSection.getParagraph(0)
//
//        // 문단에서 사각형 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
//        firstParagraph.getText().addExtendCharForGSO()
//
//        // 문단에 사각형 컨트롤 추가한다.
//        rectangle = firstParagraph.addNewGsoControl(GsoControlType.Rectangle)
//    }
//
//    private fun setCtrlHeaderGso() {
//        val hdr = rectangle!!.header
//        val prop = hdr.property
//        prop.isLikeWord = false
//        prop.isApplyLineSpace = false
//        prop.vertRelTo = VertRelTo.Para
//        prop.vertRelativeArrange = RelativeArrange.TopOrLeft
//        prop.horzRelTo = HorzRelTo.Para
//        prop.horzRelativeArrange = RelativeArrange.TopOrLeft
//        prop.isVertRelToParaLimit = true
//        prop.isAllowOverlap = true
//        prop.widthCriterion = WidthCriterion.Absolute
//        prop.heightCriterion = HeightCriterion.Absolute
//        prop.isProtectSize = false
//        prop.textFlowMethod = TextFlowMethod.TopAndBottom
//        prop.textHorzArrange = TextHorzArrange.BothSides
//        prop.objectNumberSort = ObjectNumberSort.Figure
//        hdr.setyOffset(fromMM(shapePosition.y).toLong())
//        hdr.setxOffset(fromMM(shapePosition.x).toLong())
//        hdr.width = fromMM(shapePosition.width).toLong()
//        hdr.height = fromMM(shapePosition.height).toLong()
//        hdr.setzOrder(0)
//        hdr.outterMarginLeft = 0
//        hdr.outterMarginRight = 0
//        hdr.outterMarginTop = 0
//        hdr.outterMarginBottom = 0
//        hdr.instanceId = instanceID.toLong()
//        hdr.isPreventPageDivide = false
//        hdr.explanation = null
//    }
//
//    private fun fromMM(mm: Int): Int {
//        return if (mm == 0) {
//            1
//        } else (mm.toDouble() * 72000.0f / 254.0f + 0.5f).toInt()
//    }
//
//    private fun setShapeComponent() {
//        val sc = rectangle!!.shapeComponent as ShapeComponentNormal
//        sc.offsetX = 0
//        sc.offsetY = 0
//        sc.groupingCount = 0
//        sc.localFileVersion = 1
//        sc.widthAtCreate = fromMM(shapePosition.width).toLong()
//        sc.heightAtCreate = fromMM(shapePosition.height).toLong()
//        sc.widthAtCurrent = fromMM(shapePosition.width).toLong()
//        sc.heightAtCurrent = fromMM(shapePosition.height).toLong()
//        sc.rotateAngle = 0
//        sc.rotateXCenter = fromMM(shapePosition.width / 2)
//        sc.rotateYCenter = fromMM(shapePosition.height / 2)
//        sc.createLineInfo()
//        val li = sc.lineInfo
//        li.property.lineEndShape = LineEndShape.Flat
//        li.property.startArrowShape = LineArrowShape.None
//        li.property.startArrowSize = LineArrowSize.MiddleMiddle
//        li.property.endArrowShape = LineArrowShape.None
//        li.property.endArrowSize = LineArrowSize.MiddleMiddle
//        li.property.isFillStartArrow = true
//        li.property.isFillEndArrow = true
//        li.property.lineType = LineType.None
//        li.outlineStyle = OutlineStyle.Normal
//        li.thickness = 0
//        li.color.value = 0
//        sc.createFillInfo()
//        val fi = sc.fillInfo
//        fi.type.setPatternFill(false)
//        fi.type.setImageFill(true)
//        fi.type.setGradientFill(false)
//        fi.createImageFill()
//        val imgF = fi.imageFill
//        imgF.imageFillType = ImageFillType.FitSize
//        imgF.pictureInfo.brightness = 0.toByte()
//        imgF.pictureInfo.contrast = 0.toByte()
//        imgF.pictureInfo.effect = PictureEffect.RealPicture
//        imgF.pictureInfo.binItemID = binDataID
//        sc.createShadowInfo()
//        val si = sc.shadowInfo
//        si.type = ShadowType.None
//        si.color.value = 0xc4c4c4
//        si.offsetX = 283
//        si.offsetY = 283
//        si.transparnet = 0.toShort()
//        sc.setMatrixsNormal()
//    }
//
//    private fun setShapeComponentRectangle() {
//        val scr = rectangle!!.shapeComponentRectangle
//        scr.roundRate = 0.toByte()
//        scr.x1 = 0
//        scr.y1 = 0
//        scr.x2 = fromMM(shapePosition.width)
//        scr.y2 = 0
//        scr.x3 = fromMM(shapePosition.width)
//        scr.y3 = fromMM(shapePosition.height)
//        scr.x4 = 0
//        scr.y4 = fromMM(shapePosition.height)
//    }
//
//    companion object {
//        @Throws(Exception::class)
//        @JvmStatic
//        fun main(args: Array<String>) {
//            val filename = "sample_hwp" + File.separator.toString() + "test-blank.hwp"
//            val hwpFile = HWPReader.fromFile(filename)
//            if (hwpFile != null) {
//                val tii = Inserting_Image()
//                tii.insertShapeWithImage(hwpFile)
//                val writePath = "sample_hwp" + File.separator.toString() + "test-insert-image.hwp"
//                HWPWriter.toFile(hwpFile, writePath)
//            }
//        }
//    }
//}