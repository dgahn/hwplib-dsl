package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlFooter
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlType
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.Paragraph
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.HWPCharControlExtend
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.HWPCharNormal
import kr.dogfoot.hwplib.`object`.bodytext.paragraph.text.ParaText


fun SECTION.footer(headerFooterStyle: HeaderFooterStyle = HeaderFooterStyle(), block: FOOTER.() -> Unit = {}): Unit =
    FOOTER(
        consumer, FooterBuilder(consumer.hwpFile, headerFooterStyle), headerFooterStyle.paragraphStyle
    ).visit(block)

open class FOOTER(
    override val consumer: HwpTagConsumer<*>,
    override val builder: FooterBuilder,
    private val paragraphStyle: ParagraphStyle
) : Tag {

    override operator fun String.unaryPlus() {
        text(this)
    }

    override fun text(s: String) {
        consumer.onTagText(s, builder.paragraph, paragraphStyle)
    }
}

class FooterBuilder(override val hwpFile: HWPFile, private val footerStyle: HeaderFooterStyle) : HwpTagBuilder {
    lateinit var paragraph: Paragraph

    override fun build() {
        // ToDo 현재 Section에서 머리말을 추가할수 있도록 수정되어야 함.
        val para = hwpFile.bodyText.sectionList.first().getParagraph(0)
        para.text.addExtendCharForFooter()
        val header: ControlFooter = para.addNewControl(ControlType.Footer) as ControlFooter
        header.header.createIndex = 1
        header.header.applyPage = footerStyle.headerFooterApplyPage
        header.listHeader.paraCount = footerStyle.listHeaderStyle.paraCount
        header.listHeader.textWidth = footerStyle.listHeaderStyle.textWidth.toLong()
        header.listHeader.textHeight = footerStyle.listHeaderStyle.textHeight.toLong()
        paragraph = header.paragraphList.addNewParagraph()
    }

    override fun completed() = Unit
}

fun ParaText.addExtendCharForFooter() {
    val chExtend: HWPCharControlExtend = addNewExtendControlChar()
    chExtend.code = 0x0010.toShort()
    val addition = ByteArray(12)
    addition[3] = 'f'.toByte()
    addition[2] = 'o'.toByte()
    addition[1] = 'o'.toByte()
    addition[0] = 't'.toByte()
    try {
        chExtend.addition = addition
    } catch (e: java.lang.Exception) {
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