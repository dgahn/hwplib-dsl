package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.`object`.bodytext.control.ControlSectionDefine
import kr.dogfoot.hwplib.writer.HWPWriter
import java.awt.image.BufferedImage

open class HwpTag(
    override val tagName: String,
    override val consumer: TagConsumer<*>,
    initialAttributes: Map<String, String>,
    override val namespace: String? = null,
    override val inlineTag: Boolean,
    override val emptyTag: Boolean,
    override val attributes: MutableMap<String, String> = mutableMapOf()
) : Tag {

//    override val attributes : DelegatingMap = DelegatingMap(initialAttributes, this) { consumer }

//    override val attributesEntries: Collection<Map.Entry<String, String>>
//        get() = attributes.immutableEntries
}

open class HWP(
    initialAttributes: Map<String, String>,
    override val consumer: TagConsumer<*>,
    namespace: String? = null, override val attributes: MutableMap<String, String>
) : HwpTag("hwp", consumer, initialAttributes, null, false, true, mutableMapOf())

fun <T, C : TagConsumer<T>> C.hwp(
    content: String = "",
    namespace: String? = null,
    block: HWP.() -> Unit = {}
): T = HWP(emptyMap, this, namespace, mutableMapOf()).visitAndFinalize(this, block)

fun HWPFile.body(block: HWPFile.() -> Unit) = block.invoke(this)
