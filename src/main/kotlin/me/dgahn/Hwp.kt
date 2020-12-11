package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile

open class HWP(
    override val consumer: HwpTagConsumer<*>,
    override val builder: HwpBuilder
) : Tag

class HwpBuilder(
    override val hwpFile: HWPFile
) : HwpTagBuilder {
    override fun build() = Unit
    override fun completed() {
        if(hwpFile.bodyText.sectionList.size != 1) {
            hwpFile.bodyText.sectionList.removeAt(0)
        }
    }

}

fun <T, C : HwpTagConsumer<T>> C.hwp(block: HWP.() -> Unit = {}): T =
    HWP(this, HwpBuilder(this.hwpFile)).visitAndFinalize(this, block)
