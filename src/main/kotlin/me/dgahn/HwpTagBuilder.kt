package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile

interface HwpTagBuilder {

    val hwpFile: HWPFile

    fun build()

}