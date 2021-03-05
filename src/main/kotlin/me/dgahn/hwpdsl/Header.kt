package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlHeader
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.HWPCharNormal
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText


fun SECTION.header(headerFooterStyle: HeaderFooterStyle = HeaderFooterStyle(), block: HEADER.() -> Unit = {}): Unit = HEADER(
    consumer, HeaderBuilder(consumer.hwpFile, headerFooterStyle), headerFooterStyle.paragraphStyle
).visit(block)

open class HEADER(
    override val consumer: HwpTagConsumer<*>,
    override val builder: HeaderBuilder,
    private val paragraphStyle: ParagraphStyle
) : Tag {

    override operator fun String.unaryPlus() {
        text(this)
    }

    override fun text(s: String) {
        consumer.onTagText(s, builder.paragraph, paragraphStyle)
    }
}

class HeaderBuilder(override val hwpFile: HWPFile, private val footerStyle: HeaderFooterStyle) : HwpTagBuilder {
    lateinit var paragraph: Paragraph

    override fun build() {
        // ToDo 현재 Section에서 머리말을 추가할수 있도록 수정되어야 함.
        val para = hwpFile.bodyText.sectionList.first().getParagraph(0)
        para.text.addExtendCharForHeader()
        val header: ControlHeader = para.addNewControl(ControlType.Header) as ControlHeader
        header.header.createIndex = 1
        header.header.applyPage = footerStyle.headerFooterApplyPage
        header.listHeader.paraCount = footerStyle.listHeaderStyle.paraCount
        header.listHeader.textWidth = footerStyle.listHeaderStyle.textWidth.toLong()
        header.listHeader.textHeight = footerStyle.listHeaderStyle.textHeight.toLong()
        paragraph = header.paragraphList.addNewParagraph()
    }

    override fun completed() = Unit
}

fun ParaText.addExtendCharForHeader() {
    val chExtend = addNewExtendControlChar()
    chExtend.code = 0x0010.toShort()
    val addition = ByteArray(12)
    addition[3] = 'h'.toByte()
    addition[2] = 'e'.toByte()
    addition[1] = 'a'.toByte()
    addition[0] = 'd'.toByte()
    try {
        chExtend.addition = addition
    } catch (e: Exception) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }

    for (ch in charList) {
        if (ch.code.toInt() == 0x0d) {
            charList.remove(ch)
            break
        }
    }
    val ch2: HWPCharNormal = addNewNormalChar()
    ch2.code = 0x0d.toShort()
}