package me.dgahn

fun HWP.body(
    content: String = "",
    block: BODY.() -> Unit = {}
): Unit = BODY(attributesMapOf("class", content), consumer, mutableMapOf()).visit(block)

open class BODY(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>,
                override val attributes: MutableMap<String, String>
) : HwpTag("body", consumer, initialAttributes, null, false, false, mutableMapOf())