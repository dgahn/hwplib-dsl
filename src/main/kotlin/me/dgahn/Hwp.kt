package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine
import kr.dogfoot.hwplib.writer.HWPWriter

open class HwpTag(
    override val tagName: String,
    override val consumer: TagConsumer<*>,
    initialAttributes: Map<String, String>,
    override val namespace: String? = null,
    override val inlineTag: Boolean,
    override val emptyTag: Boolean
) : Tag {

//    override val attributes : DelegatingMap = DelegatingMap(initialAttributes, this) { consumer }

//    override val attributesEntries: Collection<Map.Entry<String, String>>
//        get() = attributes.immutableEntries
}

class HwpStreamBuilder<O : HWPFile>(override val hwpFile: O) : TagConsumer<O> {

    override fun onTagStart(tag: Tag) = Unit

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) = Unit

    override fun onTagEnd(tag: Tag) = Unit

    override fun onTagText(content: CharSequence) {
        val s = hwpFile.bodyText.sectionList.first()
        val firstParagraph = s.getParagraph(0)
        firstParagraph.text.addString(content.toString())
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) = Unit

    override fun onTagComment(content: CharSequence) = Unit

    override fun finalize(): O = hwpFile

}

open class HWP(
    initialAttributes: Map<String, String>,
    override val consumer: TagConsumer<*>,
    namespace: String? = null
) : HwpTag("hwp", consumer, initialAttributes, null, false, true)

fun <O : HWPFile> O.createHwp(): TagConsumer<O> = HwpStreamBuilder(this)

fun <T, C : TagConsumer<T>> C.hwp(
    content: String = "",
    namespace: String? = null,
    block: HWP.() -> Unit = {}
): T = HWP(emptyMap, this, namespace).visitAndFinalize(this, block)

fun HWP.body(
    content: String = "",
    block: BODY.() -> Unit = {}
): Unit = BODY(attributesMapOf("class", content), consumer).visit(block)

open class BODY(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
    HwpTag("body", consumer, initialAttributes, null, false, false) {

}

fun HWPFile.build(path: String) = HWPWriter.toFile(this, path)

fun HWPFile.body(block: HWPFile.() -> Unit) = block.invoke(this)

fun HWPFile.paperSize(paperSize: PaperSize) {
    val csd = this.bodyText.sectionList.first().getParagraph(0).controlList.first() as ControlSectionDefine
    val size = when (paperSize) {
        PaperSize.B5 -> Size(51592, 72852)
        PaperSize.B4 -> Size(72852, 103180)
        PaperSize.A4 -> Size(59528, 84188)
        PaperSize.A3 -> Size(84188, 119052)
        PaperSize.LEGAL -> Size(61200, 100800)
        PaperSize.A5 -> Size(41976, 59528)
    }
    csd.pageDef.paperWidth = size.width
    csd.pageDef.paperHeight = size.height
}

enum class PaperSize {
    B5,
    B4,
    A4,
    A3,
    LEGAL,
    A5
}


data class Size(
    val width: Long,
    val height: Long
)